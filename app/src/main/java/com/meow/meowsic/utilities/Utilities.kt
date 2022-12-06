package com.meow.meowsic.utilities

import com.meow.meowsic.volley.Urls
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class Utilities {

    private val Urls = Urls()

    fun getApiUrlPlaylistId(playlistId: String): String {
        return Urls.PLAYLISTS + "/" + playlistId
    }

    fun getApiUrlTrackId(songId: String): String {
        return Urls.TRACKS + "/" + songId
    }

    fun getApiUrlTracksQuery(query: String): String {
        return Urls.SEARCH + "?q=" + query + "&type=track"
    }

    fun getApiUrlArtistsQuery(query: String): String {
        return Urls.SEARCH + "?q=" + query + "&type=artist"
    }

    fun encodeKeyword(word: String): String {
        var meow = word.replace("[^\\.\\-\\w\\s]".toRegex(), " ")
        meow = meow.replace("\\s+".toRegex(), " ")
        try {
            meow = (URLEncoder.encode(meow, "UTF-8")).replace("+", " ")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return meow
    }
}