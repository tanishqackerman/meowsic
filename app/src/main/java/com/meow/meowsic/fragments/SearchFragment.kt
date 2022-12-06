package com.meow.meowsic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.dao.TracksDao
import com.meow.meowsic.databinding.FragmentSearchBinding
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.CustomLinearLayoutManager
import com.meow.meowsic.utilities.InternetConnection
import com.meow.meowsic.volley.RequestCallback

class SearchFragment : Fragment(), RequestCallback {

    private var messageState: Int = 0
    private val Constants = Constants()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var customLinearLayoutManager: CustomLinearLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
//    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private var currentSongIndex = 0
    private lateinit var tracksDao: TracksDao
    private val InternetConnection = InternetConnection()
    private lateinit var songs: ArrayList<Songs>

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

    private fun clearSelectedPosition() {

    }

    private fun initialiseListeners() {

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

    override fun onResume() {
        super.onResume()
        manageMessageLayouts()
    }

    override fun onListRequestSuccessful(list: ArrayList<Any>?, check: Int, status: Boolean) {
        when (check) {
            Constants.SEARCH_SONGS_WITH_QUERY -> {
                if (status) {
                    songs.clear()
                    if (list != null) {
                        songs.addAll(list as ArrayList<Songs>)
                    }
                }
            }
        }
    }

    override fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {

    }
}