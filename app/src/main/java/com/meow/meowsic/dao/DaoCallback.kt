package com.meow.meowsic.dao

import com.android.volley.VolleyError

interface DaoCallback {
    fun response(response: Any?)
    fun stringResponse(response: String?)
    fun errorResponse(error: VolleyError?)
}
