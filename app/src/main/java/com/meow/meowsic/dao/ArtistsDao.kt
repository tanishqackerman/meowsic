package com.meow.meowsic.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.VolleyError
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback
import com.meow.meowsic.volley.VolleyRequest
import org.json.JSONException
import org.json.JSONObject

class ArtistsDao(context: Context?, requestCallback: RequestCallback) : VolleyRequest(context) {

    private val Utilities = Utilities()
    private val Constants = Constants()
    private var requestCallback: RequestCallback

    init {
        this.requestCallback = requestCallback
    }

    fun getArtistFromQuery(q: String) {
        val query = Utilities.encodeKeyword(q)
        val url = Utilities.getApiUrlArtistsQuery(query)
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val artists: ArrayList<Artists> = ArrayList()
                    try {
                        val jsonArray = jsonObject.getJSONObject("artists").getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {
                            val artist = Artists(jsonArray.getJSONObject(i))
                            artists.add(artist)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    requestCallback.onRequestSuccessful(
                        artists,
                        Constants.SEARCH_ARTISTS_WITH_QUERY,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }

                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onRequestSuccessful(
                        null,
                        Constants.SEARCH_ARTISTS_WITH_QUERY,
                        false
                    )
                }
            },
            Constants.METHOD_GET,
            null
        )
    }

    fun getArtistFromId(id: String?) {
        val url = Utilities.getApiUrlArtistId(id.toString())
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val artists = Artists(jsonObject)
                    requestCallback.onRequestSuccessful(
                        artists,
                        Constants.SEARCH_ARTIST_WITH_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }
                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onRequestSuccessful(
                        null,
                        Constants.SEARCH_ARTIST_WITH_ID,
                        false
                    )
                }
            },
            Constants.METHOD_GET,
            null
        )
    }

    fun getArtistTopTracks(id: String) {
        val url = Utilities.getApiUrlArtistTopTracks(id)
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val songs = ArrayList<Songs>()
                    val jsonArray = jsonObject.getJSONArray("tracks")
                    for (i in 0 until jsonArray.length()) {
                        val song = Songs(jsonArray.getJSONObject(i))
                        songs.add(song)
                    }
                    val playlist = Playlists(songs)
                    requestCallback.onRequestSuccessful(
                        playlist,
                        Constants.SEARCH_SONGS_WITH_ARTIST_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }
                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onRequestSuccessful(
                        null,
                        Constants.SEARCH_SONGS_WITH_ARTIST_ID,
                        false
                    )
                    Log.d("hehe", error.toString())
                }
            },
            Constants.METHOD_GET,
            null
        )
    }
}