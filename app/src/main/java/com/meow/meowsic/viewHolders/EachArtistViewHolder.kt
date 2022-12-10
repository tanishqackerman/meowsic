package com.meow.meowsic.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.meow.meowsic.R

class EachArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val artistcard: CardView = itemView.findViewById(R.id.artistcard)
    val artistname: TextView = itemView.findViewById(R.id.artistname)
    val artistpic: ImageView = itemView.findViewById(R.id.artistpic)
}