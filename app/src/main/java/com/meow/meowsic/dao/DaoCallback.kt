package com.meow.meowsic.dao

interface DaoCallback {
    fun response(response: Any?)
    fun stringResponse(response: String?)
//    fun errorResponse(error: VolleyError?)
}
