package com.meow.meowsic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.adapters.EachSongAdapter
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.databinding.FragmentHomeBinding
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.volley.RequestCallback

class HomeFragment : Fragment(), RequestCallback {

    private lateinit var homeAdapter: EachSongAdapter
    private lateinit var playlistDao: PlaylistDao
    private lateinit var songs: ArrayList<Songs>
    private lateinit var binding: FragmentHomeBinding

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

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }

//    override fun onListRequestSuccessful(list: ArrayList<Any>?, check: Int, status: Boolean) {
//
//    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        val playlists: Playlists = `object` as Playlists
        songs = playlists.songs!!
        homeAdapter = EachSongAdapter(context, playlists)
        binding.homerv.layoutManager = GridLayoutManager(context, 1)
        binding.homerv.setHasFixedSize(true)
        binding.homerv.adapter = homeAdapter
    }
}