package com.meow.meowsic.dao

import android.content.Context
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback
import com.meow.meowsic.volley.VolleyRequest
import org.json.JSONException
import org.json.JSONObject

class PlaylistDao(context: Context?, requestCallback: RequestCallback) : VolleyRequest(context) {

    private val Utilites = Utilities()
    private val Constants = Constants()
    private lateinit var requestCallback: RequestCallback

    init {
        this.requestCallback = requestCallback
    }

    fun getPlaylistFromPlaylistId(playlistId: Long) {
        val url = Utilites.getApiUrlPlaylistId(playlistId.toString())
        apiCallObject(url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val playlist = Playlists(jsonObject)
                    requestCallback.onObjectRequestSuccessful(
                        playlist,
                        Constants.SEARCH_PLAYLISTS_WITH_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {}

    //            fun errorResponse(error: VolleyError?) {
    //                requestCallback.onObjectRequestSuccessful(
    //                    null,
    //                    Constants.SEARCH_PLAYLISTS_WITH_ID,
    //                    false
    //                )
    //            }
            },
            Constants.METHOD_GET,
            null
        )
    }

    fun getTracksFromPlaylistId(playlistId: String) {
        val url = Utilites.getApiUrlPlaylistId(playlistId)
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val songs: ArrayList<Songs> = ArrayList()
                    try {
                        val jsonArray = jsonObject.getJSONArray("tracks")
                        for (i in 0 until jsonArray.length()) {
                            val song = Songs(jsonArray.getJSONObject(i))
                            songs.add(song)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    requestCallback.onListRequestSuccessful(
                        songs,
                        Constants.SEARCH_SONG_WITH_PLAYLIST_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }

            },
            Constants.METHOD_GET,
            null
        )
    }
}