package com.meow.meowsic.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.activities.MainActivity
//import com.meow.meowsic.adapters.SearchHistoryAdapter
import com.meow.meowsic.adapters.SearchSongAdapter
import com.meow.meowsic.dao.ArtistsDao
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.dao.TracksDao
import com.meow.meowsic.databinding.FragmentSearchBinding
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.CustomLinearLayoutManager
import com.meow.meowsic.utilities.InternetConnection
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback

class SearchFragment : Fragment(), RequestCallback {

    private var messageState: Int = 0
    private val Constants = Constants()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var customLinearLayoutManager: CustomLinearLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
//    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var searchSongAdapter: SearchSongAdapter
    private var currentSongIndex = 0
    private lateinit var tracksDao: TracksDao
    private lateinit var playlistDao: PlaylistDao
    private lateinit var artistsDao: ArtistsDao
    private val InternetConnection = InternetConnection()
    private lateinit var songs: ArrayList<Songs>
    private lateinit var artists: ArrayList<Artists>
    private lateinit var playlists: ArrayList<Playlists>
    private val Utilities = Utilities()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songs = ArrayList()
        artists = ArrayList()
        playlists = ArrayList()
        tracksDao = TracksDao(context, this)
        artistsDao = ArtistsDao(context, this)
        playlistDao = PlaylistDao(context, this)

        searchSongAdapter = SearchSongAdapter(context, object : SearchSongAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int, check: Int) {
                val song = songs[position]
                when (check) {
                    Constants.EACH_SONG_LAYOUT_CLICKED -> {
                        val playlist: Playlists = Playlists(songs, binding.querysearch.text.toString())
                        (activity as MainActivity?)?.playSongInMainActivity(position, playlist)
                        changeSelectedPosition(position + 1)
                    }
                    Constants.EACH_SONG_MENU_CLICKED, Constants.EACH_SONG_VIEW_LONG_CLICKED -> {

                    }
                    Constants.SEE_ALL_SONGS_CLICKED -> {

                    }
                    Constants.EACH_ARTIST_LAYOUT_CLICKED -> {

                    }
                    Constants.SEE_ALL_ARTISTS_CLICKED -> {

                    }
                    Constants.EACH_PLAYLIST_LAYOUT_CLICKED -> {

                    }
                    Constants.SEE_ALL_PLAYLISTS_CLICKED -> {

                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        initialise()
        initialiseListeners()
        return view
    }

    private fun initialise() {
        linearLayoutManager = LinearLayoutManager(context)
        customLinearLayoutManager = CustomLinearLayoutManager(context, object : CustomLinearLayoutManager.MyErrorListener {
            override fun onIoobeFound() {
                binding.rlNoSearchResultFound.visibility = View.VISIBLE
            }
        })
        binding.searchrv.layoutManager = customLinearLayoutManager
//        binding.searchrv.adapter = searchHistoryAdapter
        showHistory()
    }

    private fun showHistory() {
        showNoSearchHistoryMessage()
    }

    private fun performSearch() {
        currentSongIndex = 0
        clearSelectedPosition()
        linearLayoutManager.scrollToPosition(0)
        customLinearLayoutManager.scrollToPosition(0)
        val query = binding.querysearch.text.toString()
        if (query.trim().isEmpty()) {
            showHistory()
        } else {
            showProgressBar()
            if (InternetConnection.isOnline(context)) tracksDao.getTrackFromQuery(query)
            else {
                showNoInternetMessage()
            }
        }
    }

    private fun initialiseListeners() {
        binding.cross.setOnClickListener {
            binding.querysearch.text.clear()
            showHistory()
            val imgr = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imgr.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            binding.querysearch.requestFocus()
        }

        binding.querysearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imgr = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imgr.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
                binding.querysearch.clearFocus()
                performSearch()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    fun manageMessageLayouts() {
        when (messageState) {
            Constants.SHOW_NO_HISTORY -> showNoSearchHistoryMessage()
            Constants.SHOW_NO_INTERNET_CONNECTION -> showNoInternetMessage()
            Constants.SHOW_NO_SEARCH_RESULT -> showNoSearchResultFoundMessage()
            Constants.SHOW_PROGRESS_BAR -> showProgressBar()
            Constants.SHOW_RECYCLER_VIEW -> showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        binding.rlNoInternetConnection.visibility = View.GONE
        binding.rlNoSearchHistory.visibility = View.GONE
        binding.rlNoSearchResultFound.visibility = View.GONE
        binding.searchProgressbar.visibility = View.GONE
        binding.searchrv.visibility = View.VISIBLE
        messageState = Constants.SHOW_RECYCLER_VIEW
    }

    private fun showProgressBar() {
        binding.rlNoInternetConnection.visibility = View.GONE
        binding.rlNoSearchHistory.visibility = View.GONE
        binding.rlNoSearchResultFound.visibility = View.GONE
        binding.searchProgressbar.visibility = View.VISIBLE
        binding.searchrv.visibility = View.GONE
        messageState = Constants.SHOW_PROGRESS_BAR
    }

    private fun showNoSearchResultFoundMessage() {
        binding.rlNoInternetConnection.visibility = View.GONE
        binding.rlNoSearchHistory.visibility = View.GONE
        binding.rlNoSearchResultFound.visibility = View.VISIBLE
        binding.searchProgressbar.visibility = View.GONE
        binding.searchrv.visibility = View.GONE
        messageState = Constants.SHOW_NO_SEARCH_RESULT
    }

    private fun showNoInternetMessage() {
        binding.rlNoInternetConnection.visibility = View.VISIBLE
        binding.rlNoSearchHistory.visibility = View.GONE
        binding.rlNoSearchResultFound.visibility = View.GONE
        binding.searchProgressbar.visibility = View.GONE
        binding.searchrv.visibility = View.GONE
        messageState = Constants.SHOW_NO_INTERNET_CONNECTION
    }

    private fun showNoSearchHistoryMessage() {
        binding.rlNoInternetConnection.visibility = View.GONE
        binding.rlNoSearchHistory.visibility = View.VISIBLE
        binding.rlNoSearchResultFound.visibility = View.GONE
        binding.searchProgressbar.visibility = View.GONE
        binding.searchrv.visibility = View.GONE
        messageState = Constants.SHOW_NO_HISTORY
    }

    private fun clearSelectedPosition() {
//        searchSongAdapter.notifyItemChanged(searchSongAdapter.getSelectedPosition())
//        searchSongAdapter.setSelectedPosition(-1)
    }

    private fun changeSelectedPosition(index: Int) {
        searchSongAdapter.notifyItemChanged(searchSongAdapter.selectedPosition)
        currentSongIndex = index
        searchSongAdapter.selectedPosition = currentSongIndex
        searchSongAdapter.notifyItemChanged(currentSongIndex)
    }

    override fun onStart() {
        super.onStart()
        val newpos = Utilities.getSelectedPosition(context, songs, 1)
        changeSelectedPosition(newpos)
    }

    override fun onResume() {
        super.onResume()
        manageMessageLayouts()
    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        when (check) {
            Constants.SEARCH_SONGS_WITH_QUERY -> {
                if (status) {
                    songs.clear()
//                    if (binding.searchrv.adapter is SearchHistoryAdapter)
                        binding.searchrv.adapter = searchSongAdapter
                    songs.addAll(`object` as ArrayList<Songs>)
                    if (songs.size > 4) {
                        changeSelectedPosition(
                            Utilities.getSelectedPosition(
                                context,
                                arrayListOf(songs[0], songs[1], songs[2], songs[3]),
                                1
                            )
                        )
                        searchSongAdapter.changeSongData(arrayListOf(songs[0], songs[1], songs[2], songs[3]))
                    } else {
                        changeSelectedPosition(Utilities.getSelectedPosition(context, songs, 1))
                        searchSongAdapter.changeSongData(songs)
                    }
                }
                artistsDao.getArtistFromQuery(binding.querysearch.text.toString())
            }
            Constants.SEARCH_ARTISTS_WITH_QUERY -> {
                if (status) {
                    artists.clear()
                    artists.addAll(`object` as ArrayList<Artists>)
                    if (artists.size > 4) {
                        searchSongAdapter.changeArtistData(arrayListOf(artists[0], artists[1], artists[2], artists[3]))
                    } else {
                        searchSongAdapter.changeArtistData(artists)
                    }
                }
                playlistDao.getPlaylistFromQuery(binding.querysearch.text.toString())
            }
            Constants.SEARCH_PLAYLISTS_WITH_QUERY -> {
                if (status) {
                    playlists.clear()
                    playlists.addAll(`object` as ArrayList<Playlists>)
                    if (playlists.size > 4) {
                        searchSongAdapter.changePlaylistData(arrayListOf(playlists[0], playlists[1], playlists[2], playlists[3]))
                    } else {
                        searchSongAdapter.changePlaylistData(playlists)
                    }
                }
                showRecyclerView()
                if (searchSongAdapter.itemCount == 0) {
                    showNoSearchResultFoundMessage()
                }
            }
        }
    }

//    override fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
//
//    }
}