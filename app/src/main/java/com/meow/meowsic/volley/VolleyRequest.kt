package com.meow.meowsic.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.meow.meowsic.dao.DaoCallback
import com.meow.meowsic.utilities.Constants
import org.json.JSONObject

open class VolleyRequest(context: Context?) {

    private var constants = Constants()

    fun apiCallArray(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject) {
        if (method == constants.METHOD_GET) {
            val jsonArrayRequest = JsonArrayRequest (
                Request.Method.GET,
                url,
                null,
                { response -> daoCallback.response(response) }
            ) {
//                    error -> daoCallback.errorResponse(error)
            }
        }
    }

    fun apiCallObject(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject?) {
        if (method == constants.METHOD_GET) {
            val jsonObjectRequest = JsonObjectRequest (
                Request.Method.GET,
                url,
                null,
                { response -> daoCallback.response(response) }
            ) {
//                    error -> daoCallback.errorResponse(error)
            }
        }
    }
}