package com.meow.meowsic.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Playlists(var playlist: JSONObject?, songslist: ArrayList<Songs>?, typestring: String?) : Parcelable {

    var UNKNOWN: String? = "Unknown"
    var type: String? = null
    var id: String? = null
    var name: String? = null
    var artwork: String? = null
    var track_count: Int = 0
    lateinit var songs: ArrayList<Songs>

    constructor(parcel: Parcel) : this(
        null,
        null,
        null
    ) {
        UNKNOWN = parcel.readString()
        type = parcel.readString()
        id = parcel.readString()
        name = parcel.readString()
        artwork = parcel.readString()
        track_count = parcel.readInt()
        songs = ArrayList()
    }

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
                songs.add(song)
            }
        }
    }

    constructor(songslist: ArrayList<Songs>, typestring: String?) : this(null, songslist, typestring) {
        this.songs = songslist
        this.type = typestring
        this.name = typestring
    }

    constructor(songslist: ArrayList<Songs>) : this(null, songslist, null) {
        this.songs = songslist
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(UNKNOWN)
        parcel.writeString(type)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(artwork)
        parcel.writeInt(track_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Playlists> {
        override fun createFromParcel(parcel: Parcel): Playlists {
            return Playlists(parcel)
        }

        override fun newArray(size: Int): Array<Playlists?> {
            return arrayOfNulls(size)
        }
    }
}