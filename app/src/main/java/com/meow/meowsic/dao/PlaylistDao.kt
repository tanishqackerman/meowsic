package com.meow.meowsic.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.VolleyError
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback
import com.meow.meowsic.volley.VolleyRequest
import org.json.JSONException
import org.json.JSONObject

class PlaylistDao(val context: Context?, requestCallback: RequestCallback) : VolleyRequest(context) {

    private val Utilites = Utilities()
    private val Constants = Constants()
    private var requestCallback: RequestCallback

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

                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onObjectRequestSuccessful(
                        null,
                        Constants.SEARCH_PLAYLISTS_WITH_ID,
                        false
                    )
                }
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
                        Toast.makeText(context, songs.size.toString(), Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                    requestCallback.onListRequestSuccessful(
                        songs,
                        Constants.SEARCH_SONG_WITH_PLAYLIST_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }

                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onListRequestSuccessful(
                        null,
                        Constants.SEARCH_SONG_WITH_PLAYLIST_ID,
                        false
                    )
                    Toast.makeText(context, "volley error", Toast.LENGTH_SHORT).show()
                }

            },
            Constants.METHOD_GET,
            null
        )
    }
}