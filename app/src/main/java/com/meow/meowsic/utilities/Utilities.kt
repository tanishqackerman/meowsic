package com.meow.meowsic.utilities

import com.meow.meowsic.volley.Urls

class Utilities {

    private val Urls = Urls()

    fun getApiUrlPlaylistId(playlistId: String): String {
        return Urls.PLAYLISTS + "/" + playlistId
//        return Urls.PLAYLISTS + "/" + playlistId + "?limit=200&client_id=" + Urls.CLIENT_ID
    }

    fun getApiUrlTrackId(songId: String): String {
        return Urls.TRACKS + "/" + songId
//        return Urls.TRACKS + "/" + songId + "?client_id=" + Urls.CLIENT_ID
    }
}