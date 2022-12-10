package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class EachSongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val songcard: CardView = itemView.findViewById(R.id.songcard)
    val songname: TextView = itemView.findViewById(R.id.songname)
    val albumcover: ImageView = itemView.findViewById(R.id.albumcover)
    val artistname: TextView = itemView.findViewById(R.id.artistname)
}