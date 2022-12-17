package com.meow.meowsic.activities

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.databinding.ActivityMainBinding
import com.meow.meowsic.fragments.AriFragment
import com.meow.meowsic.fragments.HomeFragment
import com.meow.meowsic.fragments.PlaylistFragment
import com.meow.meowsic.fragments.SearchFragment
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.services.MusicService
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.SharedPreference
import com.meow.meowsic.volley.RequestCallback

class MainActivity : AppCompatActivity(), RequestCallback {

    private val Constants = Constants()
    private lateinit var binding: ActivityMainBinding
    var musicSrv: MusicService = MusicService()
    private var musicBound = false
    private var currentSongPosition = 0
    private lateinit var currentPlaylist: Playlists
    private lateinit var pref: SharedPreference
    private lateinit var context: Context
    private lateinit var playlistDao: PlaylistDao
    private lateinit var playIntent: Intent

    private val musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicSrv = binder.getService()
            musicBound = true
            musicSrv.setCallBacks(object : MusicService.MusicServiceInterface {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onMusicDisturbed(state: Int, song: Songs?) {
                    when (state) {
                        Constants.MUSIC_STARTED -> {
                            binding.bottomplaypause.isClickable = true
                            binding.bottomplaypause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                        }
                        Constants.MUSIC_PLAYED -> binding.bottomplaypause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                        Constants.MUSIC_PAUSED -> binding.bottomplaypause.setImageDrawable(resources.getDrawable(R.drawable.play))
                        Constants.MUSIC_ENDED -> binding.bottomplaypause.setImageDrawable(resources.getDrawable(R.drawable.play))
                        Constants.MUSIC_LOADED -> {
                            binding.songname.text = song?.name
                            binding.artistname.text = song?.artist
                            Glide.with(context).load(song?.songArtwork).into(binding.albumcover)
                            binding.bottomplaypause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                        }
                    }
                }

                override fun onSongChanged(newPosition: Int) {
                    currentSongPosition = newPosition
                }

                override fun onMusicProgress(position: Int) {
//                    progressView?.getBackground()?.level = position
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    fun playSongInMainActivity(songPosition: Int, playlist: Playlists, searchcheck: Boolean) {
        if (!searchcheck) binding.bottomplayer.isClickable = false
        if (playlist.songs.isEmpty()) {
            Toast.makeText(context, "Unable to play this Playlist.", Toast.LENGTH_SHORT).show()
            return
        }
        val song: Songs = playlist.songs[songPosition]
        currentSongPosition = songPosition
        pref.setCurrentPlayingSong(song.id)
        pref.setCurrentPlaylist(playlist)
        Toast.makeText(this, playlist.id.toString(), Toast.LENGTH_SHORT).show()
        pref.setCurrentPlayingSongPosition(songPosition)
        binding.songname.text = song.name
        currentPlaylist = playlist
        val url: String? = song.url

        musicSrv.setSongPosition(songPosition)
        musicSrv.setSongs(playlist.songs)
        musicSrv.setPlaylist(playlist)
        musicSrv.startSong()
    }

    fun updatePlaylistInMainActivity(playlist: Playlists) {
        if (playlist.id == currentPlaylist.id) {
            currentPlaylist = playlist
            pref.setCurrentPlaylist(playlist)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Meowsic)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        context = this

        playIntent = Intent(baseContext, MusicService::class.java)
        bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
        startService(playIntent)

        pref = SharedPreference(context)
        playlistDao = PlaylistDao(context, this)
        playlistDao.getPlaylistFromPlaylistId("37i9dQZF1DXcBWIGoYBM5M")
        currentPlaylist = pref.getCurrentPlaylist()!!
        currentSongPosition = pref.getCurrentPlayingSongPosition()
        val currentSong: Songs = currentPlaylist.songs[currentSongPosition]
        binding.songname.text = currentSong.name
        binding.artistname.text = currentSong.artist
        Glide.with(context)
            .load(currentSong.songArtwork)
            .into(binding.albumcover)

        initialiseListeners()
        bottomNavigation()
    }

    private fun initialiseListeners() {
        binding.bottomplaypause.setOnClickListener {
            currentPlaylist = pref.getCurrentPlaylist()!!
            musicSrv.setPlaylist(currentPlaylist)
            if (musicSrv.isPlaying()) {
                musicSrv.pausePlayer()
            } else {
                musicSrv.go()
            }
        }

        binding.bottomnext.setOnClickListener {
            currentPlaylist = pref.getCurrentPlaylist()!!
            musicSrv.setPlaylist(currentPlaylist)
            musicSrv.playNext()
        }

        binding.bottomprev.setOnClickListener {
            currentPlaylist = pref.getCurrentPlaylist()!!
            musicSrv.setPlaylist(currentPlaylist)
            musicSrv.playPrev()
        }

        binding.bottomplayer.setOnClickListener {
            val intent = Intent(context, SongPlayerActivity::class.java)
            intent.putExtra(Constants.PLAYLIST_MODEL_KEY, currentPlaylist)
            intent.putExtra(Constants.CURRENT_PLAYING_SONG_POSITION, currentSongPosition)
//            intent.putExtra(Constants.SONGS_MODEL_KEY, currentPlaylist.songs)
//            intent.putExtra(
//                Constants.IS_PLAYING,
//                musicSrv.isPlaying()
//            )
            startActivity(intent)
        }
    }

    private fun bottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.library -> replaceFragment(PlaylistFragment())
                R.id.ari -> replaceFragment(AriFragment())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        val playlists: Playlists = `object` as Playlists
        pref.setCurrentPlaylist(playlists)
        currentPlaylist = playlists
    }

    override fun onStop() {
        super.onStop()
        pref.setCurrentPlayingSong(currentPlaylist.songs[currentSongPosition].id)
        pref.setCurrentPlayingSongPosition(musicSrv.getSongPosition())
    }
}