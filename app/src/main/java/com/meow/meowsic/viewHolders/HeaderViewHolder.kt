package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playlistcover: ImageView = itemView.findViewById(R.id.playlistcover)
    val playlistname: TextView = itemView.findViewById(R.id.playlistname)
}