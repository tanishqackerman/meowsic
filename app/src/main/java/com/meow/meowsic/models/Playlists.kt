package com.meow.meowsic.models

import org.json.JSONObject

class Playlists(var playlist: JSONObject?, songslist: ArrayList<Songs>?, typestring: String?) {

    var UNKNOWN = "Unknown"
    var type: String? = null
    var id: String? = null
    var name: String? = null
    var artwork: String? = null
    var track_count: Int = 0
    var songs: ArrayList<Songs>? = null
//    var tracks: JSONObject? = JSONObject()

    constructor(playlist: JSONObject?) : this(playlist, null, null) {
        this.id = playlist?.getString("id")
        this.name = if (playlist!!.has("name")) playlist.getString("name") else UNKNOWN
        this.artwork = playlist.getJSONArray("images").getJSONObject(0).getString("url")
        this.track_count = if (playlist.getJSONObject("tracks").has("items")) playlist.getJSONObject("tracks").getJSONArray("items").length() else 0
        val tracks = if (playlist.has("tracks")) playlist.getJSONObject("tracks") else null
        songs = ArrayList()
        if (tracks != null) for (i in 0 until track_count) {
            if (track_count != 0) {
                val song = Songs(tracks.getJSONArray("items").getJSONObject(i).getJSONObject("track"))
                songs!!.add(song)
            }
        }
    }

    constructor(songslist: ArrayList<Songs>?, typestring: String?) : this(null, songslist, typestring) {
        this.songs = songslist
        this.type = typestring
        this.name = typestring
    }
}