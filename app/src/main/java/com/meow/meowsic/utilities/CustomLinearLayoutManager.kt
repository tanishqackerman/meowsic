package com.meow.meowsic.utilities

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager(
    private val context: Context?,
    private val myErrorListener: MyErrorListener
) : LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            myErrorListener.onIoobeFound()
        }
    }

    interface MyErrorListener {
        fun onIoobeFound()
    }
}
