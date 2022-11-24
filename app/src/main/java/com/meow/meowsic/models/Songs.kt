package com.meow.meowsic.models

import org.json.JSONObject
import java.io.File

class Songs(song: JSONObject) {

    var id: Long = 0
     var duration: Long = 0
//     var user_id: Long = 0
    var title: String? = null
     var artist: String? = null
     var genre: String? = null
     var album: String? = null
    var songFile: File? = null
    var permalink: String? = null
    var songArtwork: String? = null
     var streamUrl: String? = null
    var playbackCount = 0
     var likesCount: Int = 0
     var favoritesCount: Int = 0
//    var user: User? = null

    init {
        this.id = song.getLong("id")
//        this.user_id = song.getLong("user_id")
        this.duration = song.getLong("duration")
        this.title = song.getString("title").trim()
        this.genre = song.getString("genre").trim()
        this.permalink = song.getString("permalink").trim()
        this.songArtwork = song.getString("artwork_url")
        this.streamUrl = if (song.has("stream_url")) song.getString("stream_url") else ""
        this.playbackCount = if (song.has("playback_count")) song.getInt("playback_count") else 0
        this.likesCount = if (song.has("likes_count")) song.getInt("likes_count") else 0
        this.favoritesCount = if (song.has("favoritings_count")) song.getInt("favoritings_count") else 0
        if (likesCount == 0) likesCount = this.favoritesCount
    }

}