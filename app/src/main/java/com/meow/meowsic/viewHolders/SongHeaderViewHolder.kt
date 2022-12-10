package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class SongHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val songheader: TextView = itemView.findViewById(R.id.songheader)
}