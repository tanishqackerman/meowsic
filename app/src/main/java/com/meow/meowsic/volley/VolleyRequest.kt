package com.meow.meowsic.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.meow.meowsic.volley.VolleySingleton
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.meow.meowsic.dao.DaoCallback
import com.meow.meowsic.utilities.Constants
import org.json.JSONObject

open class VolleyRequest(val contexthehe: Context?) {

    private var VolleySingleton = VolleySingleton(contexthehe)
    private var constants = Constants()

    fun apiCallArray(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject) {
        if (method == constants.METHOD_GET) {
            val jsonArrayRequest = JsonArrayRequest (
                Request.Method.GET,
                url,
                null,
                { response -> daoCallback.response(response) }
            ) {
                    error -> daoCallback.errorResponse(error)
            }
        }
    }

    fun apiCallObject(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject?) {
        if (method == constants.METHOD_GET) {
            val jsonObjectRequest = JsonObjectRequest (
                Request.Method.GET,
                url,
                null,
                {
                    response -> daoCallback.response(response)
                }
            ) {
                    error -> daoCallback.errorResponse(error)
            }
            VolleySingleton.getInstance(contexthehe)?.addToRequestQueue(jsonObjectRequest)
        }
    }
}