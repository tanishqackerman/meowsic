package com.meow.meowsic.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.meow.meowsic.R
import com.meow.meowsic.adapters.HomeAdapter
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.models.Songs
import com.meow.meowsic.volley.RequestCallback

class HomeFragment : Fragment(), RequestCallback {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var playlistDao: PlaylistDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeAdapter = HomeAdapter(context, )

        playlistDao = PlaylistDao(context, this)
        playlistDao.getTracksFromPlaylistId("37i9dQZF1DXcBWIGoYBM5M")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onListRequestSuccessful(list: ArrayList<Songs>, check: Int, status: Boolean) {

    }

    override fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {

    }

}