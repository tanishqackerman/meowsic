package com.meow.meowsic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R
import com.meow.meowsic.models.Songs
import com.meow.meowsic.viewHolders.HomeViewHolder

class HomeAdapter(
    val context: Context?,
    val songs: List<Songs>,
//    val itemClickListener: ItemClickListener
    ) : RecyclerView.Adapter<HomeViewHolder>() {

//    interface ItemClickListener {
//        fun onItemClick(view: View?, position: Int, check: Int)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = songs[position]
        holder.songname.text = item.title
        holder.artistname.text = item.artist
        holder.albumcover.setImageURI(item.songArtwork?.toUri())
        if (holder.albumcover.drawable == null) holder.albumcover.setImageResource(R.drawable.rep)

    }

    override fun getItemCount(): Int {
        return songs.size
    }
}