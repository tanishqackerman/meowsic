package com.meow.meowsic.utilities

//import android.support.v7.graphics.Palette
import android.content.Context
import androidx.palette.graphics.Palette
import com.meow.meowsic.models.Songs
import com.meow.meowsic.volley.Urls
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.*

class Utilities {

    private val Urls = Urls()

    fun getApiUrlPlaylistId(playlistId: String?): String {
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

    fun getApiUrlArtistTopTracks(artistid: String): String {
        return Urls.ARTISTS + "/" + artistid + "/top-tracks?country=ES"
    }

    fun getApiUrlArtistId(artistid: String): String {
        return Urls.ARTISTS + "/" + artistid
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

    fun formatInteger(n: Int): String {
        if (n > 1000000) {
            val a = n / 1000000
            var b = n % 1000000
            b /= 100000
            val ans = StringBuilder()
            ans.append(a)
            if (b == 0) {
                return ans.append("M").toString()
            }
            ans.append(".").append(b).append("M")
            return ans.toString()
        }
        if (n > 1000) {
            val a = n / 1000
            var b = n % 1000
            b /= 100
            val ans = StringBuilder()
            ans.append(a)
            if (b == 0) {
                return ans.append("K").toString()
            }
            ans.append(".").append(b).append("K")
            return ans.toString()
        }
        return n.toString()
    }

    fun formatTime(n: Long): String {
        val s = n / 1000
        return String.format("%d:%02d", s / 60, s % 60)
    }

    fun getBackgroundColorFromPalette(palette: Palette?): Int {
        if (palette != null) {
            var background = palette.getVibrantColor(-0x9e9d9f)
//            if (background == -0x9e9d9f) background = palette.getDarkMutedColor(-0x9e9d9f)
//            if (background == -0x9e9d9f) background = palette.getVibrantColor(-0x9e9d9f)
//            if (background == -0x9e9d9f) background = palette.getMutedColor(-0x9e9d9f)
            return background
        }
        return -0x9e9d9f
    }

    fun getVibrantColorFromPalette(palette: Palette?): Int {
        if (palette != null) {
            var background = palette.getVibrantColor(-0x9e9d9f)
//            if (background == -0x9e9d9f) background = palette.getVibrantColor(-0x9e9d9f)
            return background
        }
        return -0x9e9d9f
    }

    fun formatIntWithComma(followers: Long, append: String): String {
        return NumberFormat.getNumberInstance(Locale.US).format(followers) + append
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