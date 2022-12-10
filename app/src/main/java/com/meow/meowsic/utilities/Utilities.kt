package com.meow.meowsic.utilities

import android.content.Context
import com.meow.meowsic.models.Songs
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

    fun getApiUrlPlaylistsQuery(query: String): String {
        return Urls.SEARCH + "?q=" + query + "&type=playlist"
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

    fun getSelectedPosition(context: Context?, songs: ArrayList<Songs>, offset: Int): Int {
        var offset = offset
        val pref = SharedPreference(context)
        val currentPos = pref.getCurrentPlayingSong()
        for (song in songs) {
            if (song.id  == currentPos) return offset
            offset++
        }
        return -1
    }
}