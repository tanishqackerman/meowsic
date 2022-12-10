package com.meow.meowsic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.viewHolders.HeaderViewHolder
import com.meow.meowsic.viewHolders.EachSongViewHolder

class EachSongAdapter(
    val context: Context?,
    val playlists: Playlists,
//    val itemClickListener: ItemClickListener
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Header = 1
        val Normal = 2
//    interface ItemClickListener {
//        fun onItemClick(view: View?, position: Int, check: Int)
//    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Header) {
            val v: View = LayoutInflater.from(context).inflate(R.layout.home_header, parent, false)
            HeaderViewHolder(v)
        } else {
            val v: View = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false)
            EachSongViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EachSongViewHolder) {
            val item = playlists.songs?.get(position - 1)
            holder.songname.text = item?.name
            holder.artistname.text = item?.artist
            if (context != null) {
                Glide.with(context)
                    .load(item?.songArtwork)
                    .into(holder.albumcover)
            }
            if (item?.songArtwork == null) holder.albumcover.setImageResource(R.drawable.rep)
        } else if (holder is HeaderViewHolder) {
            holder.playlistname.text = playlists.name?.lowercase()
            if (context != null) {
                Glide.with(context)
                    .load(playlists.artwork)
                    .into(holder.playlistcover)
            }
//            holder.playlistcover.setImageResource(R.drawable.rep)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) Header
        else Normal
    }

    override fun getItemCount(): Int {
        return playlists.songs?.size!! + 1
    }
}