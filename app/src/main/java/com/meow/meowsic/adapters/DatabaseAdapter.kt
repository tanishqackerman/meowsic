package com.meow.meowsic.adapters

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.meow.meowsic.models.Hybrid
import com.meow.meowsic.utilities.MySQLiteHelper
import java.util.*
import kotlin.collections.ArrayList

class DatabaseAdapter(context: Context?) {

    private lateinit var mySQLiteHelper: MySQLiteHelper
    private lateinit var onDatabaseChanged: OnDatabaseChanged
    private lateinit var onAPlaylistDataChanged: OnAPlaylistDataChanged
    private lateinit var historyList: ArrayList<Hybrid>

    init {
        mySQLiteHelper = MySQLiteHelper(context)
    }

    constructor(context: Context, onDatabaseChanged: OnDatabaseChanged) : this(context) {
        this.onDatabaseChanged = onDatabaseChanged
    }

    constructor(context: Context, onAPlaylistDataChanged: OnAPlaylistDataChanged) : this(context) {
        this.onAPlaylistDataChanged = onAPlaylistDataChanged
    }

    @SuppressLint("Range")
    fun getHistoryList(): ArrayList<Hybrid> {
        historyList = ArrayList()
        val sqLiteDatabase: SQLiteDatabase = mySQLiteHelper.readableDatabase
        val cursor = sqLiteDatabase.query(mySQLiteHelper.HISTORY_TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val history = Hybrid(
                    cursor.getInt(cursor.getColumnIndex(mySQLiteHelper.TYPE)),
                    cursor.getString(cursor.getColumnIndex(mySQLiteHelper.ID)),
                    cursor.getString(cursor.getColumnIndex(mySQLiteHelper.ARTWORK_URL)),
                    cursor.getString(cursor.getColumnIndex(mySQLiteHelper.TYPE_NAME)),
                    cursor.getString(cursor.getColumnIndex(mySQLiteHelper.ARTIST_NAME))
                )
                historyList.add(history)
            } while (cursor.moveToNext())
        }
        cursor.close()
        sqLiteDatabase.close()
        historyList.reverse()
        return historyList
    }

    fun addToHistory(history: Hybrid): Long {
        val id: Long

        deleteFromHistory(history.id)
        val contentValues = ContentValues()
        val sqLiteDatabase: SQLiteDatabase = mySQLiteHelper.getWritableDatabase()

        contentValues.put(mySQLiteHelper.TYPE, history.type)
        contentValues.put(mySQLiteHelper.ARTIST_NAME, history.songArtist)
        contentValues.put(mySQLiteHelper.TYPE_NAME, history.name)
        contentValues.put(mySQLiteHelper.ARTWORK_URL, history.artworkUrl)
        contentValues.put(mySQLiteHelper.ID, history.id)

        id = sqLiteDatabase.insert(mySQLiteHelper.HISTORY_TABLE, null, contentValues)
        sqLiteDatabase.close()
        return id
    }

    fun deleteFromHistory(id: String?): Long {
        val sqLiteDatabase: SQLiteDatabase = mySQLiteHelper.writableDatabase
        var selection: String? = mySQLiteHelper.ID + " = ?"

        var selectionArgs: Array<String>? = null
        if (id == null) selection = null else selectionArgs = arrayOf(id.toString())
        return sqLiteDatabase.delete(mySQLiteHelper.HISTORY_TABLE, selection, selectionArgs).toLong()
    }

    interface OnDatabaseChanged {
        fun onPlaylistAdded()

        fun onPlaylistDeleted(position: Int)

        fun onPlaylistUpdated(position: Int)
    }

    interface OnAPlaylistDataChanged {
        fun onASongAdded(playlistId: Int)

        fun onASongRemoved(playlistId: Int, position: Int)
    }
}