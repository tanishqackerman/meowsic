package com.meow.meowsic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.adapters.SeeAllAdapter
import com.meow.meowsic.databinding.FragmentSeeAllBinding
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants

class SeeAllFragment : Fragment() {

    private lateinit var binding: FragmentSeeAllBinding
    private var TYPE: Int? = 0
    private lateinit var songs: ArrayList<Songs>
    private lateinit var artists: ArrayList<Artists>
    private lateinit var playlists: ArrayList<Playlists>
    private val Constants = Constants()
    private lateinit var seeAllAdapter: SeeAllAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        TYPE = bundle?.getInt("type")
        songs = bundle?.getParcelableArrayList(Constants.SONGS_MODEL_KEY)!!
        artists = bundle.getParcelableArrayList(Constants.ARTIST_MODEL_KEY)!!
        playlists = bundle.getParcelableArrayList(Constants.PLAYLIST_MODEL_KEY)!!

        seeAllAdapter = SeeAllAdapter(context, songs, artists, playlists, object : SeeAllAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int, check: Int) {

            }
        }, TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_see_all, container, false)
        binding = FragmentSeeAllBinding.bind(view)
        initialise()
        return view
    }

    private fun initialise() {
//        when (TYPE) {
//            Constants.TYPE_TRACK -> {
//
//            }
//        }

        binding.seeallrv.adapter = seeAllAdapter
        binding.seeallrv.layoutManager = LinearLayoutManager(context)

    }
}