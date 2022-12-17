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
    ) : RecyclerView.Adapter<EachSongViewHolder>() {

    var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EachSongViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false)
        return EachSongViewHolder(v)
    }

    override fun onBindViewHolder(holder: EachSongViewHolder, position: Int) {
        val item = playlists.songs[position]
        holder.songname.text = item.name
        holder.artistname.text = item.artist
        if (context != null) {
            Glide.with(context)
                .load(item.songArtwork)
                .into(holder.albumcover)
        }
        if (item.songArtwork == null) holder.albumcover.setImageResource(R.drawable.rep)
    }

    override fun getItemCount(): Int {
        return playlists.songs.size
    }
}