package com.meow.meowsic.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
//import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.adapters.EachSongAdapter
import com.meow.meowsic.backgroundTask.ColorPaletteFromImage
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.databinding.PlaylistPageBinding
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback

class HomeFragment : Fragment(), RequestCallback {

    private lateinit var homeAdapter: EachSongAdapter
    private lateinit var playlistDao: PlaylistDao
    private lateinit var songs: ArrayList<Songs>
    private lateinit var binding: PlaylistPageBinding
    private lateinit var colorPaletteFromImage: ColorPaletteFromImage
    private val Utilities = Utilities()
    private lateinit var gd: GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playlistDao = PlaylistDao(context, this)
        playlistDao.getPlaylistFromPlaylistId("37i9dQZF1DXcBWIGoYBM5M")
        songs = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.playlist_page, container, false)
        binding = PlaylistPageBinding.bind(view)

        gd = GradientDrawable()
        colorPaletteFromImage = ColorPaletteFromImage(context, object : ColorPaletteFromImage.PaletteCallback {
            override fun onPostExecute(palette: Palette?) {
                changeBackground(Utilities.getBackgroundColorFromPalette(palette))
            }
        })

        return view
    }

//    override fun onListRequestSuccessful(list: ArrayList<Any>?, check: Int, status: Boolean) {
//
//    }

    fun changeBackground(color: Int) {
        gd.colors = intArrayOf(color, context?.resources?.getColor(R.color.bg)!!)
        gd.gradientType = GradientDrawable.RADIAL_GRADIENT
        gd.gradientRadius = 500f
        binding.appbarlayout.background = gd
    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        if (status) {
            val playlists: Playlists = `object` as Playlists
            songs = playlists.songs
            colorPaletteFromImage.execute(playlists.artwork)
            Glide.with(requireContext())
                .load(playlists.artwork)
                .into(binding.playlistcover)
            binding.playlistname.text = playlists.name?.lowercase()
            homeAdapter = EachSongAdapter(context, playlists)
            binding.homerv.layoutManager = GridLayoutManager(context, 1)
            binding.homerv.setHasFixedSize(true)
            binding.homerv.adapter = homeAdapter
        }
    }
}