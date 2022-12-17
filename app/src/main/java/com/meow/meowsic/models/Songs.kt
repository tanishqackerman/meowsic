package com.meow.meowsic.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Songs(song: JSONObject?) : Parcelable {

    var id: String? = null
    var duration: Long = 0
    var name: String? = null
    var artist: String? = null
    var album: String? = null
    var songArtwork: String? = null
    var url: String? = null

    constructor(parcel: Parcel) : this(null) {
        id = parcel.readString()
        duration = parcel.readLong()
        name = parcel.readString()
        artist = parcel.readString()
        album = parcel.readString()
        songArtwork = parcel.readString()
        url = parcel.readString()
    }

    init {
        this.id = song?.getString("id")
        this.duration = song?.getLong("duration_ms")!!
        this.name = song.getString("name").trim()
        this.url = song.getString("preview_url")
        this.songArtwork = song.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url")
        this.album = song.getJSONObject("album").getString("name")
        this.artist = song.getJSONArray("artists").getJSONObject(0).getString("name")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(duration)
        parcel.writeString(name)
        parcel.writeString(artist)
        parcel.writeString(album)
        parcel.writeString(songArtwork)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Songs> {
        override fun createFromParcel(parcel: Parcel): Songs {
            return Songs(parcel)
        }

        override fun newArray(size: Int): Array<Songs?> {
            return arrayOfNulls(size)
        }
    }
}