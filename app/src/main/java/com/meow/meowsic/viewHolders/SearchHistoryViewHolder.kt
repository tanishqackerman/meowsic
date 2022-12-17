package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val image: ImageView = itemView.findViewById(R.id.image)
    val artistname: TextView = itemView.findViewById(R.id.artistname)
    val cross: ImageView = itemView.findViewById(R.id.cross)
    val itemcard: CardView = itemView.findViewById(R.id.itemcard)
}