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
            val jsonObjectRequest = object : JsonObjectRequest (
                Request.Method.GET,
                url,
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        daoCallback.response(response)
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
//                        refreshAccessToken(url)
                        daoCallback.errorResponse(error)
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer BQAUaOeBrk6XjvbQX8G8d_4Bsj9g_kwT9btNaiFkoupe3dYlJhsa3Xa_75DXdKfCEiez1Wg-KBzga6QZGPc0nFjVmqxhcOzigM6HE1G1VGjzraeG1xju-snxpHrBX1ud3F4oDntRtiub8NRpZEkuIR26y1fX4x1HCoFIn7G8egpvXXRB0dcDehBkfhvzwN2o1Or0ThRtAzJoEKXtsv7FfdMRJYr_aRC08ZxXkycSxlOQNf9MtpCQN8j_52jDoTBRnoJl8i5AN_bpWQ"
                    return headers
                }
            }
            VolleySingleton.getInstance(contexthehe)?.addToRequestQueue(jsonObjectRequest)
        }
    }

//    fun apiCallObject(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject?) {
//        if (method == constants.METHOD_GET) {
//            val jsonRequest = JsonObjectRequest(
//                Request.Method.POST, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                YOUR, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                SERVER, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                URL, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                Parameters
//            )if (); any”,
//            object : Response.Listener<JSONObject?> {
//                override fun onResponse(response: JSONObject?) {
//                    //store auth token you have received and pass it in subsequent requests
//                }
//
//                init {
//                    var getHeaders: Map<String?, String?>
//                    ()
//                    AuthFailureError
//                    run {
//                        headers.put("Accept", "application/json")
//                        headers.put(
//                            "Authorization",
//                            "Bearer " +, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                            Your, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */,
//                            Token, /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */
//                        )
//                    }
//                }
//            }
//        }, object : com.android.volley.Response.ErrorListener{
//            fun onErrorResponse(error: VolleyError?) {
//                // check for error and retry
//            }
//        })
//            VolleySingleton.getInstance(contexthehe)?.addToRequestQueue(jsonObjectRequest)
//        }
//    }

//    fun apiCallObject(url: String, daoCallback: DaoCallback, method: Int, jsonObject: JSONObject?) {
//        if (method == constants.METHOD_GET) {
//            val jsonObjectRequest = JsonObjectRequest (
//                Request.Method.GET,
//                url,
//                null,
//                object : Response.Listener<JSONObject> {
//                    override fun onResponse(response: JSONObject?) {
//                        daoCallback.response(response)
//                    }
//
//                    init {
//                        var getHeaders: Map<String?, String?>
//                        ()
//                        AuthFailureError
//                        run {
//                            headers.put("Accept", "application/json")
//                            headers.put(
//                                "Authorization",
//                                "Bearer " + "YourToken")
//                        }
//                    }
//                }
//            ) {
//                    error -> daoCallback.errorResponse(error)
//            }
//            VolleySingleton.getInstance(contexthehe)?.addToRequestQueue(jsonObjectRequest)
//        }
//    }

//    val blogRequest: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, object : Response.Listener<JSONObject?>() {
//            override fun onResponse(response: JSONObject?) {
//                // successfully got blog response
//            }
//        }, object : Response.ErrorListener() {
//            override fun onErrorResponse(error: VolleyError) {
//                if (error.networkResponse.statusCode === 401) {
//                    refreshAccessToken()
//                } else {
//                    // irrecoverable errors. show error to user.
//                }
//            }
//        }) {
//            override fun getHeaders(): Map<String?, String?>? {
//                val headers: Map<String?, String> = HashMap()
//                headers.put("Authorization", "Bearer $accessToken")
//                return headers
//            }
//        }
//    val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
//        Response.Listener<String> { response ->
//            Log.d("A", "Response is: " + response.substring(0,500))
//        },
//        Response.ErrorListener {  })
//    {
//        override fun getHeaders(): MutableMap<String, String> {
//            val headers = HashMap<String, String>()
//            headers["Authorization"] = "Basic <<YOUR BASE64 USER:PASS>>"
//            return headers
//        }
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