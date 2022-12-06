package com.meow.meowsic.models

import org.json.JSONObject

class Artists(artist: JSONObject) {

    var name: String? = null
    var followers: Long? = 0
    var image: String? = null

    init {
        this.name = artist.getString("name")
        this.followers = artist.getJSONObject("followers").getLong("total")
        this.image = artist.getJSONArray("images").getJSONObject(0).getString("url")
    }
}