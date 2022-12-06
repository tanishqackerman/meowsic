package com.meow.meowsic.models

import android.net.Uri
import org.json.JSONObject
import java.io.File

class Songs(song: JSONObject) {

    var id: String? = null
    var duration: Long = 0
    var name: String? = null
    var artist: String? = null
    var album: String? = null
    var songArtwork: String? = null
    var url: String? = null
    var isHeader: Boolean = false
    var isNormal: Boolean = false

    init {
        this.id = song.getJSONObject("track").getString("id")
        this.duration = song.getJSONObject("track").getLong("duration_ms")
        this.name = song.getJSONObject("track").getString("name").trim()
        this.url = song.getJSONObject("track").getString("uri")
        this.songArtwork = song.getJSONObject("track").getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url")
        this.album = song.getJSONObject("track").getJSONObject("album").getString("name")
        this.artist = song.getJSONObject("track").getJSONArray("artists").getJSONObject(0).getString("name")
    }
}