package com.meow.meowsic.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.models.Hybrid
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.viewHolders.SearchHistoryViewHolder
import com.meow.meowsic.viewHolders.SongFooterViewHolder
import com.meow.meowsic.viewHolders.SongHeaderViewHolder

class SearchHistoryAdapter(context: Context?, historyList: ArrayList<Hybrid>, itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HISTORY_HEADER = 0
    private val TYPE_HISTORY_FOOTER = 2
    private val TYPE_HISTORY_DATA = 1
    private var context: Context?
    private var itemClickListener: ItemClickListener
    private var historyList: ArrayList<Hybrid>
    private val Constants = Constants()

    init {
        this.context = context
        this.historyList = historyList
        this.itemClickListener = itemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return TYPE_HISTORY_HEADER
        if (position <= historyList.size) return TYPE_HISTORY_DATA
        return if (position == historyList.size + 1) TYPE_HISTORY_FOOTER else TYPE_HISTORY_HEADER

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HISTORY_HEADER) {
            return SongHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.searchsongheader, parent, false))
        } else if (viewType == TYPE_HISTORY_FOOTER) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.searchsongfooter, parent, false)
            val songFooterViewHolder: SongFooterViewHolder = SongFooterViewHolder(view)
            songFooterViewHolder.songfooter.setOnClickListener {
                itemClickListener.onItemClick(
                    view,
                    songFooterViewHolder.adapterPosition,
                    Constants.CLEAR_HISTORY_CLICKED
                )
            }
            return songFooterViewHolder
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_history_item, parent, false)
            val searchHistoryViewHolder: SearchHistoryViewHolder = SearchHistoryViewHolder(view)
            val transitionView: View = searchHistoryViewHolder.image
            searchHistoryViewHolder.itemcard.setOnClickListener {
                itemClickListener.onItemClick(transitionView, searchHistoryViewHolder.adapterPosition - 1, Constants.HISTORY_LAYOUT_CLICKED)
            }
            searchHistoryViewHolder.cross.setOnClickListener {
                itemClickListener.onItemClick(view, searchHistoryViewHolder.adapterPosition - 1, Constants.HISTORY_CROSS_CLICKED)
            }
            return searchHistoryViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HISTORY_HEADER -> {
                val songHeaderViewHolder = holder as SongHeaderViewHolder
                songHeaderViewHolder.songheader.text = "Recent Searches"
            }
            TYPE_HISTORY_FOOTER -> {
                val songFooterViewHolder = holder as SongFooterViewHolder
                songFooterViewHolder.songfooter.text = "Clear Recent Searches"
            }
            TYPE_HISTORY_DATA -> {
                val searchHistoryViewHolder = holder as SearchHistoryViewHolder
                val history = historyList[position - 1]
                searchHistoryViewHolder.name.text = history.name
                if (context != null) {
                    Glide.with(context!!)
                        .load(history.artworkUrl)
                        .into(searchHistoryViewHolder.image)
                }

                when (history.type) {
                    Constants.TYPE_TRACK -> {
                        searchHistoryViewHolder.artistname.text = history.songArtist
                    }
                    Constants.TYPE_ARTIST -> {
                        searchHistoryViewHolder.artistname.text = "Artist"
                    }
                    Constants.TYPE_PLAYLIST -> {
                        searchHistoryViewHolder.artistname.text = "Playlist"
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (historyList == null || historyList.size == 0) 0 else historyList.size + 2
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHistory(historyList: ArrayList<Hybrid>) {
        this.historyList = historyList
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteFromHistory(position: Int, historyList: ArrayList<Hybrid>) {
        this.historyList = historyList
        if (historyList.size == 0) this.notifyDataSetChanged() else this.notifyItemRemoved(position)
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int, check: Int)
    }
}