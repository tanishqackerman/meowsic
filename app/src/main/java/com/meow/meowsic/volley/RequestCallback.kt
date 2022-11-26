package com.meow.meowsic.volley

import com.meow.meowsic.models.Songs

interface RequestCallback {
    fun onListRequestSuccessful(list: ArrayList<Songs>?, check: Int, status: Boolean)
    fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean)
}
