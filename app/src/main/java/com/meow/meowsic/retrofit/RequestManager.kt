package com.meow.meowsic.retrofit

import com.meow.meowsic.models.playlistmodel.Playlist
import com.meow.meowsic.models.trackmodel.Track
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class RequestManager {

    interface CallPlaylist {
        @GET("playlists")
        fun callPlaylist(
            @Query("playlist_id") playlist_id: String
        ): Call<Playlist>
    }
    interface CallTrack {
        @GET("tracks")
        fun callTrack(
            @Query("id") id: String
        ): Call<Track>
    }
}