package com.meow.meowsic.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.*
import android.util.Log
import android.widget.Toast
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.SharedPreference
import java.io.IOException

class MusicService : Service(), OnCompletionListener, OnAudioFocusChangeListener,
    OnBufferingUpdateListener, OnPreparedListener, OnErrorListener,
    OnSeekCompleteListener {

    private val Constants = Constants()
    private val musicBind: IBinder = MusicBinder()
    private lateinit var mp: MediaPlayer
    private lateinit var songs: List<Songs>
    private var songPosition = 0
    private lateinit var playlist: Playlists
    private lateinit var pref: SharedPreference
    private lateinit var context: Context
    private var musicServiceInterface: MusicService.MusicServiceInterface? =null
    private var viewMusicInterface: MusicService.ViewMusicInterface? = null
    private var state = 0
    private lateinit var audioManager: AudioManager
//    private val notificationGenerator: NotificationGenerator? = null
    private lateinit var shuffleSongList: ArrayList<Int>
    private lateinit var seekBarTask: AsyncTask<Void, Int, Int>
    private lateinit var  handler: Handler

    override fun onBind(intent: Intent): IBinder {
        return musicBind
    }

    override fun onCreate() {
        super.onCreate()

        initPlayer()
        initialise()
    }

    private fun initialise() {
        context = this
        pref = SharedPreference(context)
        playlist = pref.getCurrentPlaylist()!!
        state = -1
        songs = playlist.songs
        songPosition = pref.getCurrentPlayingSongPosition()
//        shuffleSongList = pref.getc
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun initPlayer() {
        mp = MediaPlayer()
        mp.setWakeMode(
            applicationContext,
            PowerManager.PARTIAL_WAKE_LOCK
        )
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mp.setOnErrorListener(this)
        mp.setOnCompletionListener(this)
        mp.setOnBufferingUpdateListener(this)
        mp.setOnPreparedListener(this)
        mp.setOnSeekCompleteListener(this)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mp.stop()
        mp.release()
        return false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        context = this
        pref = SharedPreference(context)
        playlist = pref.getCurrentPlaylist()!!
        state = -1
        songs = playlist.songs
        songPosition = pref.getCurrentPlayingSongPosition()
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return START_STICKY
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {

    }

    override fun onCompletion(p0: MediaPlayer?) {
        if (mp.currentPosition > 0) {
            if (songPosition != playlist.songs.size - 1) mp.reset()
            playNext()
        }
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        mp.reset()
        return false
    }

    override fun onPrepared(p0: MediaPlayer?) {
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        mp.start()
        setState(Constants.MUSIC_STARTED)
        handler.postDelayed(object : Runnable {
            override fun run() {
                val current: Int = getPosition()
                viewMusicInterface?.onMusicProgress(current / 100)
                if (songPosition < playlist.songs.size) musicServiceInterface?.onMusicProgress((current * 10000 / playlist.songs[songPosition].duration).toInt())
                handler.postDelayed(this, 100)
            }
        }, 100)
    }

    override fun onSeekComplete(p0: MediaPlayer?) {

    }

    fun setCallBacks(musicServiceInterface: MusicServiceInterface) {
        this.musicServiceInterface = musicServiceInterface
    }

    fun setViewMusicCallbacks(viewMusicInterface: ViewMusicInterface) {
        this.viewMusicInterface = viewMusicInterface
    }

    fun startSong() {
        val song: Songs = playlist.songs[songPosition]
//        showNotification(song)
        setState(Constants.MUSIC_LOADED)
        musicServiceInterface!!.onSongChanged(songPosition)
        if (viewMusicInterface != null) {
            viewMusicInterface!!.onSongChanged(songPosition)
        }
        mp.reset()
        val url: String? = song.url
        pref.setCurrentPlayingSong(song.id)
        try {
            mp.setDataSource(url)
            mp.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setSongs(songs: ArrayList<Songs>) {
        this.songs = songs
    }

    fun getSongPosition(): Int {
        return songPosition
    }

    fun setSongPosition(songPosition: Int) {
        this.songPosition = songPosition
    }

    fun getPosition(): Int {
        return mp.currentPosition
    }

    fun getDur(): Int {
        return mp.duration
    }

    fun isPlaying(): Boolean {
        return mp.isPlaying
    }

    fun pausePlayer() {
        mp.pause()
        setState(Constants.MUSIC_PAUSED)
//        notificationGenerator.updateView(false, songPosition)
    }

    fun seek(posn: Int) {
        mp.seekTo(posn)
    }

    fun go() {
        if (state != -1) {
            audioManager.requestAudioFocus(
                this,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
            mp.start()
            setState(Constants.MUSIC_PLAYED)
//            notificationGenerator.updateView(true, songPosition)
        } else {
            startSong()
        }
    }

    fun playPrev() {
        if (pref.getIsShuffleOn()) {
//            songPosition = FisherYatesShuffle.getPreviousShufflePosition(context)
        } else {
            if (songPosition == 0) {
                if (pref.getIsRepeatOn()) songPosition = playlist.songs.size - 1 else {
                    setState(Constants.MUSIC_ENDED)
//                    notificationGenerator.updateView(false, songPosition)
                }
            } else {
                songPosition--
            }
        }
        startSong()
    }

    //skip to next
    fun playNext() {
        if (pref.getIsShuffleOn()) {
//            songPosition = FisherYatesShuffle.getNextShufflePosition(context)
        } else {
            if (songPosition == playlist.songs.size - 1) {
                if (pref.getIsRepeatOn()) songPosition = 0 else {
                    setState(Constants.MUSIC_ENDED)
//                    notificationGenerator.updateView(false, songPosition)
                }
            } else {
                songPosition++
            }
        }
        startSong()
    }

    override fun onDestroy() {
        stopForeground(true)
    }

    override fun onAudioFocusChange(i: Int) {
        when (i) {
            AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> if (getState() == Constants.MUSIC_PLAYED) go()
            AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pausePlayer()
        }
    }

    private fun getState(): Int {
        return state
    }

    private fun setState(state: Int) {
        this.state = state
        musicServiceInterface!!.onMusicDisturbed(state, playlist.songs[songPosition])
        if (viewMusicInterface != null) {
            viewMusicInterface!!.onMusicDisturbed(state, playlist.songs[songPosition])
        }
    }

    fun setPlaylist(playlist: Playlists) {
        this.playlist = playlist
    }

    interface MusicServiceInterface {
        fun onMusicDisturbed(state: Int, song: Songs?)
        fun onSongChanged(newPosition: Int)
        fun onMusicProgress(position: Int)
    }

    interface ViewMusicInterface {
        fun onMusicDisturbed(state: Int, song: Songs?)
        fun onSongChanged(newPosition: Int)
        fun onMusicProgress(position: Int)
    }

    //binder
    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

}