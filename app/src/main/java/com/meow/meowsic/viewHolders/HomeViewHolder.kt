package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val songname: TextView = itemView.findViewById(R.id.songname)
    val albumcover: ImageView = itemView.findViewById(R.id.albumcover)
    val artistname: TextView = itemView.findViewById(R.id.artistname)
}