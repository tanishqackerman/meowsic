package com.meow.meowsic.fragments

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.activities.MainActivity
import com.meow.meowsic.adapters.DatabaseAdapter
import com.meow.meowsic.adapters.SearchHistoryAdapter
import com.meow.meowsic.adapters.SearchSongAdapter
import com.meow.meowsic.dao.ArtistsDao
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.dao.TracksDao
import com.meow.meowsic.databinding.FragmentSearchBinding
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Hybrid
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
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
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
    private lateinit var databaseAdapter: DatabaseAdapter
    private lateinit var currentHistoryPlaylist: Playlists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songs = ArrayList()
        artists = ArrayList()
        playlists = ArrayList()
        tracksDao = TracksDao(context, this)
        artistsDao = ArtistsDao(context, this)
        playlistDao = PlaylistDao(context, this)
        databaseAdapter = DatabaseAdapter(context)

        searchSongAdapter = SearchSongAdapter(context, object : SearchSongAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int, check: Int) {
                val song = songs[position]
                val seeAllFragment: SeeAllFragment
                val artistFragment: ArtistFragment
                val bundle: Bundle
                when (check) {
                    Constants.EACH_SONG_LAYOUT_CLICKED -> {
                        val playlist: Playlists = Playlists(songs, binding.querysearch.text.toString())
                        val added: Long = databaseAdapter.addToHistory(Hybrid(Constants.TYPE_TRACK, song.id, song.songArtwork, song.name, song.artist))
                        (activity as MainActivity).playSongInMainActivity(position, playlist, false)
                        changeSelectedPosition(position + 1)
                    }
                    Constants.EACH_SONG_MENU_CLICKED, Constants.EACH_SONG_VIEW_LONG_CLICKED -> {
                        val popupmenu = PopupMenu(ContextThemeWrapper(context, R.style.PopupMenu), view)
                        popupmenu.menuInflater.inflate(R.menu.songpopupmenu, popupmenu.menu)
                        popupmenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.viewartist -> {
                                    Toast.makeText(context, "View Artist", Toast.LENGTH_SHORT)
                                        .show()
//                                    databaseAdapter.addToHistory(
//                                        Hybrid(
//                                            Constants.TYPE_ARTIST,
//                                            song.artist.getId(),
//                                            song.artist.getUserAvatar(),
//                                            song.artist.getUsername(),
//                                            null
//                                        )
//                                    )
//                                    val userPageFragment = UserPageFragment()
//                                    val bundle = Bundle()
//                                    bundle.putInt(Constants.TYPE, Constants.TYPE_USER)
//                                    bundle.putLong(
//                                        Constants.USER_ID_KEY,
//                                        songs[position].getUser().getId()
//                                    )
//                                    userPageFragment.setArguments(bundle)
//                                    fragmentManager!!.beginTransaction().replace(
//                                        R.id.mainFrameContainer,
//                                        userPageFragment,
//                                        Constants.FRAGMENT_USER_PAGE
//                                    )
//                                        .addToBackStack(userPageFragment.getClass().getName())
//                                        .commit()
                                }
                                R.id.addtoplaylist -> {
                                    Toast.makeText(context, "Add to Playlist", Toast.LENGTH_SHORT)
                                        .show()
                                    showAddToPlaylistDialog(song)
                                }
                            }
                            false
                        }
                        popupmenu.show()
                    }
                    Constants.SEE_ALL_SONGS_CLICKED -> {
                        seeAllFragment = SeeAllFragment()
                        bundle = Bundle()
                        bundle.putInt("type", Constants.TYPE_TRACK)
                        bundle.putString("search", binding.querysearch.text.toString())
                        bundle.putParcelableArrayList(Constants.SONGS_MODEL_KEY, songs)
                        bundle.putParcelableArrayList(Constants.ARTIST_MODEL_KEY, artists)
                        bundle.putSerializable(Constants.PLAYLIST_MODEL_KEY, playlists)
                        seeAllFragment.arguments = bundle
                        fragmentManager?.beginTransaction()?.replace(R.id.frame, seeAllFragment, Constants.FRAGMENT_SEE_ALL)?.addToBackStack(seeAllFragment.javaClass.name)?.commit()
                    }
                    Constants.EACH_ARTIST_LAYOUT_CLICKED -> {
                        databaseAdapter.addToHistory(Hybrid(Constants.TYPE_ARTIST, artists[position].id, artists[position].image, artists[position].name, null))
                        artistFragment = ArtistFragment()
                        bundle = Bundle()
                        bundle.putInt(Constants.TYPE, Constants.TYPE_ARTIST)
                        bundle.putParcelable(Constants.ARTIST_MODEL_KEY, artists[position])
                        artistFragment.arguments = bundle
                        fragmentManager!!.beginTransaction().replace(R.id.frame, artistFragment, Constants.FRAGMENT_USER_PAGE).addToBackStack(artistFragment.javaClass.name).commit()
                    }
                    Constants.SEE_ALL_ARTISTS_CLICKED -> {
                        seeAllFragment = SeeAllFragment()
                        bundle = Bundle()
                        bundle.putInt("type", Constants.TYPE_ARTIST)
                        bundle.putString("search", binding.querysearch.text.toString())
                        bundle.putParcelableArrayList(Constants.SONGS_MODEL_KEY, songs)
                        bundle.putParcelableArrayList(Constants.ARTIST_MODEL_KEY, artists)
                        bundle.putSerializable(Constants.PLAYLIST_MODEL_KEY, playlists)
                        seeAllFragment.arguments = bundle
                        fragmentManager?.beginTransaction()?.replace(R.id.frame, seeAllFragment, Constants.FRAGMENT_SEE_ALL)?.addToBackStack(seeAllFragment.javaClass.name)?.commit()
                    }
                    Constants.EACH_PLAYLIST_LAYOUT_CLICKED -> {
                        databaseAdapter.addToHistory(Hybrid(Constants.TYPE_PLAYLIST, playlists[position].id, playlists[position].artwork, playlists[position].name, null))
                        artistFragment = ArtistFragment()
                        bundle = Bundle()
                        bundle.putInt(Constants.TYPE, Constants.TYPE_PLAYLIST)
                        bundle.putParcelable(Constants.PLAYLIST_MODEL_KEY, playlists[position])
                        artistFragment.arguments = bundle
                        fragmentManager!!.beginTransaction().replace(R.id.frame, artistFragment, Constants.FRAGMENT_USER_PAGE).addToBackStack(artistFragment.javaClass.name).commit()
                    }
                    Constants.SEE_ALL_PLAYLISTS_CLICKED -> {
                        seeAllFragment = SeeAllFragment()
                        bundle = Bundle()
                        bundle.putInt("type", Constants.TYPE_PLAYLIST)
                        bundle.putString("search", binding.querysearch.text.toString())
                        bundle.putParcelableArrayList(Constants.SONGS_MODEL_KEY, songs)
                        bundle.putParcelableArrayList(Constants.ARTIST_MODEL_KEY, artists)
                        bundle.putSerializable(Constants.PLAYLIST_MODEL_KEY, playlists)
                        seeAllFragment.arguments = bundle
                        fragmentManager?.beginTransaction()?.replace(R.id.frame, seeAllFragment, Constants.FRAGMENT_SEE_ALL)?.addToBackStack(seeAllFragment.javaClass.name)?.commit()
                    }
                }
            }
        })

        searchHistoryAdapter = SearchHistoryAdapter(context, databaseAdapter.getHistoryList(), object : SearchHistoryAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int, check: Int) {
                val historyList = databaseAdapter.getHistoryList()
                val history: Hybrid
                when (check) {
                    Constants.HISTORY_CROSS_CLICKED -> {
                        if (position >= 0) {
                            history = historyList[position]
                            databaseAdapter.deleteFromHistory(history.id)
                            searchHistoryAdapter.deleteFromHistory(
                                position + 1,
                                databaseAdapter.getHistoryList()
                            )
                            if (databaseAdapter.getHistoryList().size == 0) {
                                binding.rlNoSearchHistory.visibility = View.VISIBLE
                            }
                        }
                    }
                    Constants.HISTORY_LAYOUT_CLICKED -> {
                        history = historyList[position]
                        when (history.type) {
                            Constants.TYPE_TRACK -> tracksDao.getTrackFromId(history.id)
                            Constants.TYPE_ARTIST -> artistsDao.getArtistFromId(history.id)
                            Constants.TYPE_PLAYLIST -> playlistDao.getPlaylistFromPlaylistId(history.id)
                        }
                    }
                    Constants.CLEAR_HISTORY_CLICKED -> {
                        databaseAdapter.deleteFromHistory(null)
                        searchHistoryAdapter.updateHistory(java.util.ArrayList<Hybrid>())
                        binding.rlNoSearchHistory.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun showAddToPlaylistDialog(song: Songs) {

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
        binding.searchrv.adapter = searchHistoryAdapter
        showHistory()
    }

    private fun showHistory() {
        if (databaseAdapter.getHistoryList().size == 0) showNoSearchHistoryMessage()
        else showRecyclerView()
        searchHistoryAdapter.updateHistory(databaseAdapter.getHistoryList())
        binding.searchrv.adapter = searchHistoryAdapter
        binding.searchrv.clearFocus()
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
        binding.querysearch.text.clear()
        manageMessageLayouts()
    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        when (check) {
            Constants.SEARCH_SONGS_WITH_QUERY -> {
                if (status) {
                    songs.clear()
                    if (binding.searchrv.adapter is SearchHistoryAdapter) binding.searchrv.adapter = searchSongAdapter
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
            Constants.SEARCH_SONG_WITH_ID -> {
                val song: Songs = `object` as Songs
                if (activity != null) {
                    val songs: ArrayList<Songs> = ArrayList<Songs>()
                    songs.add(song)
                    val playlist = Playlists(songs, "History")
                    (activity as MainActivity?)!!.playSongInMainActivity(0, playlist, false)
                }
            }
            Constants.SEARCH_ARTIST_WITH_ID -> {
                val artists = `object` as Artists
                val artistFragment = ArtistFragment()
                val bundle = Bundle()
                bundle.putInt(Constants.TYPE, Constants.TYPE_ARTIST)
                bundle.putParcelable(Constants.ARTIST_MODEL_KEY, artists)
                artistFragment.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(R.id.frame, artistFragment, Constants.FRAGMENT_USER_PAGE)?.addToBackStack(artistFragment.javaClass.name)?.commit()
            }
            Constants.SEARCH_PLAYLISTS_WITH_ID -> {
                currentHistoryPlaylist = `object` as Playlists
                val artistFragment = ArtistFragment()
                val bundle = Bundle()
                bundle.putInt(Constants.TYPE, Constants.TYPE_PLAYLIST)
                bundle.putParcelable(Constants.PLAYLIST_MODEL_KEY, currentHistoryPlaylist)
                artistFragment.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(R.id.frame, artistFragment, Constants.FRAGMENT_USER_PAGE)?.addToBackStack(artistFragment.javaClass.name)?.commit()
            }
        }
    }

//    override fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
//
//    }
}