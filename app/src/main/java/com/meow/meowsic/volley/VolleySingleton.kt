package com.meow.meowsic.volley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class VolleySingleton(val context: Context?) {

    private var mInstance: VolleySingleton? = null
    private var mRequestQueue: RequestQueue? = Volley.newRequestQueue(context?.applicationContext)

    @Synchronized
    fun getInstance(context: Context?): VolleySingleton? {
//        mInstance = VolleySingleton(context!!)
        if (mInstance == null) {
            mInstance = VolleySingleton(context)
        }
        return mInstance
    }

    private fun getRequestQueue(): RequestQueue? {
//        mRequestQueue = Volley.newRequestQueue(context.applicationContext)
        return mRequestQueue
    }

    fun <T> addToRequestQueue(request: Request<T>?) {
        getRequestQueue()?.add(request)
    }

//    private var mInstance: VolleySingleton? = null
//    private var mRequestQueue: RequestQueue? = null
//
//    private fun VolleySingleton(context: Context) {
//        mRequestQueue = Volley.newRequestQueue(context.applicationContext)
//    }
//
//    @Synchronized
//    fun getInstance(context: Context): VolleySingleton? {
//        if (mInstance == null) {
//            mInstance = VolleySingleton(context)
//        }
//        return mInstance
//    }
//
//    fun getRequestQueue(): RequestQueue? {
//        return mRequestQueue
//    }

//    fun refreshAccessToken(url: String) {
//        val params = JSONObject()
//        try {
//            params.put("client_id", "\${your client id here}")
//            params.put("client_secret", "\${your client secret here}")
//            params.put("refresh_token", "\${your refresh token here}")
//            params.put("grant_type", "refresh_token")
//        } catch (ignored: JSONException) {
//            // never thrown in this case
//        }
//        val refreshTokenRequest = JsonObjectRequest(
//            Request.Method.GET,
//            url,
//            params,
//            { response ->
//                try {
//                    val accessToken = response?.getString("access_token")
//                    //                        fetchBlogs(accessToken)
//                } catch (_: JSONException) {
//
//                }
//            }
//        ) { error -> // show error to user. refresh failed.
//            Log.e("Error on token refresh", String(error.networkResponse.data))
//        }
//        mRequestQueue?.add(refreshTokenRequest)
//    }
}