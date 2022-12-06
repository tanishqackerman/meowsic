package com.meow.meowsic.models

import org.json.JSONArray
import org.json.JSONObject

class Playlists(playlist: JSONObject) {

    var UNKNOWN = "Unknown"
    var type: String? = null
    var id: String? = null
    var name: String? = null
    var artwork: String? = null
    var track_count: Int = 0
    var songs: ArrayList<Songs>
//    var tracks: JSONObject? = JSONObject()

    init {
        this.id = playlist.getString("id")
        this.name = if (playlist.has("name")) playlist.getString("name") else UNKNOWN
        this.artwork = playlist.getJSONArray("images").getJSONObject(0).getString("url")
        this.track_count = playlist.getJSONObject("tracks").getJSONArray("items").length()
        val tracks = if (playlist.has("tracks")) playlist.getJSONObject("tracks") else null
        songs = ArrayList()
        if (tracks != null) for (i in 0 until track_count) {
            val song = Songs(tracks.getJSONArray("items").getJSONObject(i))
            songs.add(song)
        }
    }
}