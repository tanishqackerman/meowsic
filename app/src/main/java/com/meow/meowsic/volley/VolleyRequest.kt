package com.meow.meowsic.volley

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.meow.meowsic.dao.DaoCallback
import com.meow.meowsic.utilities.Constants
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.Url


open class VolleyRequest(val contexthehe: Context?) {

    private var VolleySingleton = VolleySingleton(contexthehe)
    private var Constants = Constants()
    private var Urls = Urls()

    fun apiCallArray(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject) {
        if (method == Constants.METHOD_GET) {
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
        if (method == Constants.METHOD_GET) {
            val jsonObjectRequest = object : JsonObjectRequest (
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONObject> { response -> daoCallback.response(response) },
                Response.ErrorListener { error ->
//                    refreshAccessToken(url)
                    daoCallback.errorResponse(error)
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer BQAN7HIfAXX_mnR8m8F06_psUEkpNFuOb8X1K9EvD3n-qZM8UMBrkqMAIhhornmahpX4U_n1_zgBaK_rEYcLK0_osA812tYK9cmPiX8at-GaOaiFGH9uU08zpEofiVq_UhrfzT9MLDpC1nYqYumJ8j7sKmlny3cUoG7TPhbC1lAMKysPcncZab0zjRoI8c0upaA5dpk28rb0wzvkHbHb47kfzrbaVQclyJmWG5oxOHBS2LHXyWMa4lLJLneZZnjrjXpisRVraQboag"
                    return headers
                }
            }
            VolleySingleton.getInstance(contexthehe)?.addToRequestQueue(jsonObjectRequest)
        }
    }

//    fun refreshAccessToken(url: String) {
//        val params = JSONObject()
//        try {
//            params.put("client_id", Urls.CLIENT_ID)
//            params.put("client_secret", Urls.CLIENT_SECRET)
//            params.put("refresh_token", Urls.REFRESH_TOKEN)
//            params.put("grant_type", "refresh_token")
//        } catch (ignored: JSONException) {
//            // never thrown in this case
//        }
//        val refreshTokenRequest = JsonObjectRequest(
//            Request.Method.POST, url, params,
//            { response ->
//                try {
//                    val accessToken = response.getString("access_token")
//                } catch (e: JSONException) {
//                    // this will never happen but if so, show error to user.
//                }
//            }
//        ) { _ ->
//            Toast.makeText(contexthehe, "token error", Toast.LENGTH_SHORT).show()
//        }
//    }
}