package com.meow.meowsic.activities

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.adapters.ViewPagerAdapter
import com.meow.meowsic.backgroundTask.ColorPaletteFromImage
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.dao.TracksDao
import com.meow.meowsic.databinding.ActivitySongPlayerBinding
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.services.MusicService
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.utilities.ZoomOutPageTransformer
import com.meow.meowsic.volley.RequestCallback


class SongPlayerActivity : AppCompatActivity(), RequestCallback {

    private lateinit var binding: ActivitySongPlayerBinding
    private lateinit var colorPaletteFromImage: ColorPaletteFromImage
    private val Utilities = Utilities()
    private val Constants = Constants()
    private lateinit var gd: GradientDrawable
    private lateinit var currentPlaylist: Playlists
    private var currentPlayingPosition: Int = 0
    private lateinit var currentPlayingSong: Songs
    private lateinit var songs: ArrayList<Songs>
    private lateinit var playlistDao: PlaylistDao
    private lateinit var tracksDao: TracksDao
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var musicSrv: MusicService
    private var musicBound = true
    private lateinit var playIntent: Intent

    private val musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder

            musicSrv = binder.getService()
            musicBound = true
            musicSrv.setViewMusicCallbacks(object : MusicService.ViewMusicInterface {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onMusicDisturbed(state: Int, song: Songs?) {
                    when (state) {
                        Constants.MUSIC_STARTED -> {
                            binding.playpause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                            binding.slider.isEnabled = true
                            binding.playpause.isClickable = true
                        }
                        Constants.MUSIC_PLAYED -> binding.playpause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                        Constants.MUSIC_PAUSED -> binding.playpause.setImageDrawable(resources.getDrawable(R.drawable.play))
                        Constants.MUSIC_ENDED -> binding.playpause.setImageDrawable(resources.getDrawable(R.drawable.play))
                        Constants.MUSIC_LOADED -> {
                            binding.songname.text = song?.name
                            binding.artistname.text = song?.artist
//                            tvDuration.setText(Utilities.formatTime(song.getDuration()))
//                            binding.setText(Utilities.formatTime(0))
                            binding.slider.progress = 0
                            binding.slider.isEnabled = false
                            binding.playpause.isClickable = false
                            binding.slider.max = 290
                            binding.playpause.setImageDrawable(resources.getDrawable(R.drawable.pause))
                        }
                    }
                }

                override fun onSongChanged(newPosition: Int) {
                    currentPlayingPosition = newPosition
                    binding.viewPager.setCurrentItem(newPosition, true)
                }

                override fun onMusicProgress(position: Int) {
                    binding.slider.progress = position
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Meowsic)
        binding = ActivitySongPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playIntent = Intent(baseContext, MusicService::class.java)
        bindService(playIntent, musicConnection, BIND_AUTO_CREATE)
        startService(playIntent)

        playlistDao = PlaylistDao(this, this)
        tracksDao = TracksDao(this, this)

        gd = GradientDrawable()
        colorPaletteFromImage = ColorPaletteFromImage(this, object : ColorPaletteFromImage.PaletteCallback {
            override fun onPostExecute(palette: Palette?) {
                changeBackground(Utilities.getVibrantColorFromPalette(palette))
            }
        })

        val intent = intent
        currentPlaylist = intent.getParcelableExtra(Constants.PLAYLIST_MODEL_KEY)!!
        currentPlayingPosition = intent.getIntExtra(Constants.CURRENT_PLAYING_SONG_POSITION, 0)
        if (currentPlaylist.id != null) playlistDao.getPlaylistFromPlaylistId(currentPlaylist.id)
        else if (currentPlaylist.type != null) tracksDao.getTrackFromQuery(currentPlaylist.type!!)

        initialiseListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(playIntent)
        unbindService(musicConnection)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initialise() {
        if (songs.size > 0) currentPlayingSong = songs[currentPlayingPosition]
        binding.blurBg.setRenderEffect(RenderEffect.createBlurEffect(10F, 10F, Shader.TileMode.MIRROR))
        colorPaletteFromImage.execute(currentPlayingSong.songArtwork)
        binding.songname.text = currentPlayingSong.name
        binding.artistname.text = currentPlayingSong.artist
        binding.playlistname.text = currentPlaylist.name
        Glide.with(this).load(currentPlayingSong.songArtwork).into(binding.blurBg)

        binding.viewPager.setPageTransformer(false, ZoomOutPageTransformer())
        viewPagerAdapter = ViewPagerAdapter(this, songs)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.currentItem = currentPlayingPosition
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initialiseListener() {
        binding.playlistadd.setOnClickListener {

        }
        binding.playerprev.setOnClickListener {
            if (currentPlayingPosition - 1 >= 0) {
                currentPlayingPosition--
                binding.viewPager.setCurrentItem(currentPlayingPosition, true)
            }
            musicSrv.playPrev()
        }
        binding.playpause.setOnClickListener {
            if (musicSrv.getState() != Constants.MUSIC_LOADED) {
                if (musicSrv.isPlaying()) {
                    musicSrv.pausePlayer()
                } else {
                    musicSrv.go()
                }
            }
        }
        binding.playernext.setOnClickListener {
            if (currentPlayingPosition + 1 < songs.size) {
                currentPlayingPosition++
                binding.viewPager.setCurrentItem(currentPlayingPosition, true)
            }
            musicSrv.playNext()
        }
        binding.like.setOnClickListener {

        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position > currentPlayingPosition) while (position > currentPlayingPosition) musicSrv.playNext()
                else if (position < currentPlayingPosition) musicSrv.playPrev()
                currentPlayingPosition = position
                changeBackground(songs[currentPlayingPosition].songArtwork)
                Glide.with(applicationContext).load(songs[currentPlayingPosition].songArtwork).into(binding.blurBg)

                binding.songname.text = songs[currentPlayingPosition].name
                binding.artistname.text = songs[currentPlayingPosition].artist
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicSrv.seek(progress * 100)
                }
                binding.durstart.text = Utilities.formatTime(progress.toLong() * 100)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                musicSrv.pausePlayer()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                musicSrv.go()
            }

        })
    }

    fun changeBackground(color: Int) {
        gd.colors = intArrayOf(this.resources?.getColor(R.color.trans)!!, color)
        binding.cardView.background = gd
    }

    fun changeBackground(url: String?) {
        val colorPaletteFromImage = ColorPaletteFromImage(this, object : ColorPaletteFromImage.PaletteCallback {
                override fun onPostExecute(palette: Palette?) {
                    changeBackground(Utilities.getBackgroundColorFromPalette(palette))
                }
            })
        colorPaletteFromImage.execute(url)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        when (check) {
            Constants.SEARCH_PLAYLISTS_WITH_ID -> {
                currentPlaylist = `object` as Playlists
                songs = currentPlaylist.songs
//                binding.viewPager.currentItem = currentPlayingPosition
            }
            Constants.SEARCH_SONGS_WITH_QUERY -> {
                songs = `object` as ArrayList<Songs>
            }
        }
        initialise()
    }
}