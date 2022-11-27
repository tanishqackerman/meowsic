package com.meow.meowsic.models

import org.json.JSONObject

class Playlists(playlist: JSONObject) {

    var UNKNOWN = "Unknown"
    var type: String? = null
    var playlist_id: Long
    var duration: Long
//    var user_id: Long
//    var genre: String
//    var permalink: String
    var title: String
    var artwork_url: String
//    var track_count: Int
    var likes_count:Int
    val songs: List<Songs>
//    var user: User? = null
    var selfMade = false
//    var playlistArtworkBlob: ByteArray

    init {
        this.playlist_id = playlist.getLong("id")
        this.duration = if (playlist.has("duration")) playlist.getLong("duration") else 0
//        this.user_id = playlist.getLong("user_id")
//        this.genre = if (playlist.has("genre")) playlist.getString("genre") else UNKNOWN
//        this.permalink = if (playlist.has("permalink")) playlist.getString("permalink") else UNKNOWN
        this.title = if (playlist.has("title")) playlist.getString("title") else UNKNOWN
        this.artwork_url = if (playlist.has("artwork_url")) playlist.getString("artwork_url") else UNKNOWN
//        this.track_count = if (playlist.has("track_count")) playlist.getInt("track_count") else 0
        this.likes_count = if (playlist.has("likes_count")) playlist.getInt("likes_count") else 0
        val tracks = if (playlist.has("tracks")) playlist.getJSONArray("tracks") else null
        songs = ArrayList()
//        if (tracks != null) for (i in 0 until tracks.length()) {
//            val song = Songs(tracks.getJSONObject(i))
//            songs.add(song)
//        }
    }


}