package com.meow.meowsic.utilities

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteHelper(context: Context?) : SQLiteOpenHelper(context, "meowsicdb", null, 1) {

    val TYPE = "type"
    val ID = "id"
    val ARTWORK_URL = "artwork_url"
    val ARTIST_NAME = "artist_name"
    val TYPE_NAME = "type_name"
    val HISTORY_TABLE = "history_table"

    val PLAYLISTS_LIST_TABLE = "playlists_list_table"
    val PLAYLIST_ID = "playlist_id"
    val PLAYLIST_TITLE = "playlist_title"
    val PLAYLIST_TRACK_COUNT = "playlist_track_count"

    val SONGS_TABLE = "song_table"
    val SONG_ID = "song_id"
    val SONG_ARTIST_ID = "song_user_id"
    val SONG_DURATION = "song_duration"
    val SONG_TITLE = "song_title"
    val SONG_ARTIST_NAME = "song_artist_name"
    val SONG_ARTWORK_URL = "song_artwork_url"
    val SONG_STREAM_URL = "song_stream_url"
    val SONG_PLAYBACK_COUNT = "song_playback_count"

    val CREATE_HISTORY_TABLE = "CREATE TABLE " +
            HISTORY_TABLE + " (" +
            ID + " VARCHAR , " +
            TYPE + " INTEGER , " +
            ARTWORK_URL + " VARCHAR , " +
            ARTIST_NAME + " VARCHAR , " +
            TYPE_NAME + " VARCHAR )"
    val DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS $HISTORY_TABLE"

    val CREATE_PLAYLISTS_LIST_TABLE = "CREATE TABLE " +
            PLAYLISTS_LIST_TABLE + " (" +
            PLAYLIST_ID + " VARCHAR , " +
            PLAYLIST_TITLE + " VARCHAR , " +
            PLAYLIST_TRACK_COUNT + " INTEGER , " +
            ARTWORK_URL + " VARCHAR )"
    val DROP_PLAYLISTS_LIST_TABLE =
        "DROP TABLE IF EXISTS $PLAYLISTS_LIST_TABLE"

    val CREATE_SONGS_TABLE = "CREATE TABLE " +
            SONGS_TABLE + " (" +
            SONG_ID + " VARCHAR , " +
            PLAYLIST_ID + " VARCHAR , " +
            SONG_ARTIST_ID + " VARCHAR , " +
            SONG_DURATION + " INTEGER , " +
            SONG_TITLE + " VARCHAR , " +
            SONG_ARTIST_NAME + " VARCHAR , " +
            SONG_ARTWORK_URL + " VARCHAR , " +
            SONG_STREAM_URL + " VARCHAR , " +
            SONG_PLAYBACK_COUNT + " INTEGER ) "
    val DROP_SONGS_TABLE = "DROP TABLE IF EXISTS $SONGS_TABLE"

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_HISTORY_TABLE)
            db.execSQL(CREATE_PLAYLISTS_LIST_TABLE)
            db.execSQL(CREATE_SONGS_TABLE)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL(DROP_HISTORY_TABLE)
            db.execSQL(DROP_PLAYLISTS_LIST_TABLE)
            db.execSQL(DROP_SONGS_TABLE)
            onCreate(db)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

}