package com.meow.meowsic.models

import android.net.Uri
import org.json.JSONObject
import java.io.File

//class Songs(song: JSONObject) {
class Songs(id: String, duration: Long, name: String, url: String, artist: String, album: String, songArtwork: String) {

    var id: String? = null
     var duration: Long = 0
    var name: String? = null
     var artist: String? = null
     var album: String? = null
    var songArtwork: String? = null
     var url: String? = null

//    init {
//        this.id = song.getLong("id")
////        this.user_id = song.getLong("user_id")
//        this.duration = song.getLong("duration_ms")
//        this.name = song.getString("name").trim()
////        this.genre = song.getString("genre").trim()
////        this.permalink = song.getString("permalink").trim()
////        this.songArtwork = song.getString("artwork_url")
//        this.url = if (song.has("uri")) song.getString("uri") else ""
////        this.playbackCount = if (song.has("playback_count")) song.getInt("playback_count") else 0
////        this.likesCount = if (song.has("likes_count")) song.getInt("likes_count") else 0
////        this.favoritesCount = if (song.has("favoritings_count")) song.getInt("favoritings_count") else 0
////        if (likesCount == 0) likesCount = this.favoritesCount
////        this.added_at = song.getString("added_at")
//    }

    init {
        this.id = id
        this.name = name
        this.duration = duration
        this.url = url
        this.artist = artist
        this.album = album
        this.songArtwork = songArtwork
    }
}