package com.meow.meowsic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Utilities

class ViewPagerAdapter(val context: Context, val songs: ArrayList<Songs>) : PagerAdapter() {

    val Utilities = Utilities()

    override fun getCount(): Int {
        return songs.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var itemView: View? = null

        itemView = layoutInflater.inflate(R.layout.viewpagerimage, container, false)
        var imageView: ImageView? = null
        if (itemView != null) {
            imageView = itemView.findViewById<ImageView>(R.id.viewpagerimage)
            Glide.with(context)
                .load(songs[position].songArtwork)
                .placeholder(R.drawable.rep)
                .centerCrop()
                .into(imageView)
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}