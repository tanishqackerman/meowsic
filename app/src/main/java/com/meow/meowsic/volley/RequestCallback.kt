package com.meow.meowsic.volley

import com.meow.meowsic.models.Songs

interface RequestCallback {
    //    fun onListRequestSuccessful(list: ArrayList<Any>?, check: Int, status: Boolean)
    fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean)
}
