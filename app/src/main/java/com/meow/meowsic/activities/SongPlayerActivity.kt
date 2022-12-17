package com.meow.meowsic.activities

import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.adapters.ViewPagerAdapter
import com.meow.meowsic.backgroundTask.ColorPaletteFromImage
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.databinding.ActivitySongPlayerBinding
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
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
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Meowsic)
        binding = ActivitySongPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playlistDao = PlaylistDao(this, this)

        gd = GradientDrawable()
        colorPaletteFromImage = ColorPaletteFromImage(this, object : ColorPaletteFromImage.PaletteCallback {
            override fun onPostExecute(palette: Palette?) {
                changeBackground(Utilities.getVibrantColorFromPalette(palette))
            }
        })

        val intent = intent
        currentPlaylist = intent.getParcelableExtra(Constants.PLAYLIST_MODEL_KEY)!!
        currentPlayingPosition = intent.getIntExtra(Constants.CURRENT_PLAYING_SONG_POSITION, 0)
        playlistDao.getPlaylistFromPlaylistId(currentPlaylist.id)

        initialiseListener()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initialise() {
        if (songs.size > 0) currentPlayingSong = songs[currentPlayingPosition]
        binding.blurBg.setRenderEffect(RenderEffect.createBlurEffect(10F, 10F, Shader.TileMode.MIRROR))
        colorPaletteFromImage.execute(currentPlayingSong.songArtwork)
        binding.songname.text = currentPlayingSong.name
        binding.artistname.text = currentPlayingSong.artist
        binding.playlistname.text = currentPlaylist.name
//        Glide.with(this).load(currentPlayingSong.songArtwork).into(binding.albumcover)
        Glide.with(this).load(currentPlayingSong.songArtwork).into(binding.blurBg)

        binding.viewPager.setPageTransformer(false, ZoomOutPageTransformer())
        viewPagerAdapter = ViewPagerAdapter(this, songs)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.currentItem = currentPlayingPosition
    }

    private fun initialiseListener() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position > currentPlayingPosition) {
//                    musicService.playNext()
                } else if (position < currentPlayingPosition) {
//                    musicService.playPrev()
                }
                currentPlayingPosition = position
                changeBackground(songs[currentPlayingPosition].songArtwork)
                Glide.with(this@SongPlayerActivity).load(songs[currentPlayingPosition].songArtwork).into(binding.blurBg)
                binding.songname.text = songs[currentPlayingPosition].name
                binding.artistname.text = songs[currentPlayingPosition].artist
            }

            override fun onPageScrollStateChanged(state: Int) {}
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
        currentPlaylist = `object` as Playlists
        songs = currentPlaylist.songs

        initialise()
    }
}