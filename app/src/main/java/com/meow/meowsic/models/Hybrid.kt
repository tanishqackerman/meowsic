package com.meow.meowsic.models

class Hybrid(type: Int, id: String?, artwork: String?, name: String?, songArtist: String?) {
    var type = 0
    var artworkUrl: String?
    var name: String?
    var songArtist: String?
    var id: String?

    init {
        this.type = type
        this.id = id
        this.artworkUrl = artwork
        this.name = name
        this.songArtist = songArtist
    }
}