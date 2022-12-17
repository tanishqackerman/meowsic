package com.meow.meowsic.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Artists(artist: JSONObject?) : Parcelable {

    var name: String? = null
    var followers: Long = 0
    var image: String? = null
    var id: String? = null

    constructor(parcel: Parcel) : this(null) {
        name = parcel.readString()
        followers = parcel.readLong()
        image = parcel.readString()
        id = parcel.readString()
    }

    init {
        this.name = artist?.getString("name")
        this.followers = artist?.getJSONObject("followers")?.getLong("total")!!
        this.image = artist.getJSONArray("images").getJSONObject(0).getString("url")
        this.id = artist.getString("id")
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeLong(followers)
        dest.writeString(image)
        dest.writeString(id)
    }

    companion object CREATOR : Parcelable.Creator<Artists> {
        override fun createFromParcel(parcel: Parcel): Artists {
            return Artists(parcel)
        }

        override fun newArray(size: Int): Array<Artists?> {
            return arrayOfNulls(size)
        }
    }
}