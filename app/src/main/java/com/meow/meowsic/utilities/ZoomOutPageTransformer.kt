package com.meow.meowsic.utilities

import android.view.View
import androidx.viewpager.widget.ViewPager

class ZoomOutPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val normalizedposition = Math.abs(Math.abs(position) - 1)
        view.scaleX = normalizedposition * 0.15f + 0.85f
        view.scaleY = normalizedposition * 0.15f + 0.85f
        view.alpha = normalizedposition * 0.5f + 0.5f
    }
}