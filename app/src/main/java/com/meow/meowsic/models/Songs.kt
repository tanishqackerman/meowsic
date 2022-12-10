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
        this.id = song.getString("id")
        this.duration = song.getLong("duration_ms")
        this.name = song.getString("name").trim()
        this.url = song.getString("uri")
        this.songArtwork = song.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url")
        this.album = song.getJSONObject("album").getString("name")
        this.artist = song.getJSONArray("artists").getJSONObject(0).getString("name")
    }
}