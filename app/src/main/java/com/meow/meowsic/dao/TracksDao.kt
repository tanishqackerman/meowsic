package com.meow.meowsic.dao

import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback
import com.meow.meowsic.volley.VolleyRequest
import org.json.JSONObject

class TracksDao : VolleyRequest() {

    private val Utilites = Utilities()
    private val Constants = Constants()
    private lateinit var requestCallback: RequestCallback

    fun getTrackFromId(songId: Long) {
        val url = Utilites.getApiUrlTrackId(songId.toString())
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val songs = Songs(jsonObject)
                    requestCallback.onObjectRequestSuccessful(
                        songs,
                        Constants.SEARCH_SONG_WITH_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }
                //fun errorResponse(error: VolleyError?) {
                //                requestCallback.onObjectRequestSuccessful(
                //                    null,
                //                    Constants.SEARCH_SONG_WITH_ID,
                //                    false
                //                )
                //            }
            },
            Constants.METHOD_GET,
            null
        )
    }
}