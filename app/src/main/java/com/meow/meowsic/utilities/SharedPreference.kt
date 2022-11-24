package com.meow.meowsic.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.meow.meowsic.models.Playlists

class SharedPreference(context: Context) {

    private val CURRENT_PLAYING_SONG_ID = "currentPlayingSongId"
    private val CURRENT_PLAYLIST = "currentSongList"
    private val CURRENT_PLAYING_SONG_POSITION = "currentPlayingSongPosition"

    private val SHUFFLE_ON = "shuffleOn"
    private val REPEAT_ON = "repeatOn"
    private val CURRENT_PLAYLIST_SHUFFLE_STRING = "playlistShuffleArray"
    private val CURRENT_SHUFFLE_SONG_POSITION = "currentShuffleSongPosition"

    private val PRIVATE_MODE = 0

    private lateinit var pref: SharedPreferences

    init {
        pref = context.getSharedPreferences("meowsic", PRIVATE_MODE)
    }

    fun getCurrentPlayingSong(): Long {
        return pref.getLong(CURRENT_PLAYING_SONG_ID, 0)
    }

    @SuppressLint("CommitPrefEdits")
    fun setCurrentPlayingSong(songId: Long) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putLong(CURRENT_PLAYING_SONG_ID, songId)
        editor.apply()
    }

    fun getIsShuffleOn(): Boolean {
        return pref.getBoolean(SHUFFLE_ON, false)
    }

    fun setIsShuffleOn(shuffleOn: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(SHUFFLE_ON, shuffleOn)
        editor.apply()
    }

    fun getIsRepeatOn(): Boolean {
        return pref.getBoolean(REPEAT_ON, false)
    }

    fun setIsRepeatOn(repeatOn: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(REPEAT_ON, repeatOn)
        editor.apply()
    }

    fun getCurrentPlaylist(): Playlists {
        val playlistJson = pref.getString(CURRENT_PLAYLIST, null)
        val gson = Gson()
        return gson.fromJson(playlistJson, Playlists::class.java)
    }

    fun setCurrentPlaylist(playlist: Playlists) {
        val gson = Gson()
        val playlistJson: String = gson.toJson(playlist)
        val editor = pref.edit()
        editor.putString(CURRENT_PLAYLIST, playlistJson)
        editor.apply()
    }

    fun getCurrentPlayingSongPosition(): Int {
        return pref.getInt(CURRENT_PLAYING_SONG_POSITION, 0)
    }

    fun setCurrentPlayingSongPosition(position: Int) {
        val editor = pref.edit()
        editor.putInt(CURRENT_PLAYING_SONG_POSITION, position)
        editor.apply()
    }
}