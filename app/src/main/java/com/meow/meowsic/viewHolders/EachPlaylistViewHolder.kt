package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class EachPlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playlistcard: CardView = itemView.findViewById(R.id.playlistcard)
    val playlistname: TextView = itemView.findViewById(R.id.playlistname)
    val albumcover: ImageView = itemView.findViewById(R.id.albumcover)
    val options: ImageView = itemView.findViewById(R.id.options)
}