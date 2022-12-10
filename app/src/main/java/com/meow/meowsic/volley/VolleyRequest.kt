package com.meow.meowsic.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.meow.meowsic.dao.DaoCallback
import com.meow.meowsic.utilities.Constants
import org.json.JSONObject


open class VolleyRequest(val contexthehe: Context?) {

    private var VolleySingleton = VolleySingleton(contexthehe)
    private var Constants = Constants()
    private var Urls = Urls()

    fun apiCallArray(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject?) {
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
                    headers["Authorization"] = "Bearer BQAX12wiKp459OdDlqr_a82oeedakjNPMqSsLSTSnf1CVmNFylwjcli0gb22lfcl5H8hsWHmuItI-dJQyvc_JZThPqSrTzQguy7iOWPEMIUky8Tux3Mlk8qy880ONdzBBqBW2LR3T7WXgglnnY9gIIpIDGZlsQ37FCF1WCi_UtFaX4NyqcGEToVLbKZrpkXaIjbuBnln2avcS_aId92c7ksxOBkbU_XnHbsvuSR93Rsr6J1AzqIsKmDKnd938iXHhbdXFnISnV95eA"
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