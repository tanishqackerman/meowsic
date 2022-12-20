package com.meow.meowsic.receivers

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.meow.meowsic.R
import com.meow.meowsic.activities.MainActivity
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.utilities.Constants

class NotificationGenerator {

    private lateinit var remoteViews: RemoteViews
    private lateinit var bigRemoteView:RemoteViews
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private val Constants = Constants()

    private var currentSongPosition = 0
    private var isPlaying = true

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RemoteViewLayout", "UnspecifiedImmutableFlag")
    fun showNotification(context: Context, currentSongPosition: Int, currentPlaylist: Playlists, bitmap: Bitmap?) {
        this.currentSongPosition = currentSongPosition

        remoteViews = RemoteViews(context.packageName, R.layout.notificationbar)
        remoteViews.setImageViewBitmap(R.id.albumcover, bitmap)

        notificationBuilder = NotificationCompat.Builder(context.applicationContext, Constants.NOTIFICATION_SONG_CHANNEL_ID)

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(Constants.FROM_NOTIFICATION, true)
        intent.putExtra(Constants.PLAYLIST_MODEL_KEY, currentPlaylist)
        intent.putExtra(Constants.CURRENT_PLAYING_SONG_POSITION, currentSongPosition)
        intent.putExtra(Constants.IS_PLAYING, isPlaying)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.meow)
            .setContentTitle("meowsic")
            .setContentText(currentPlaylist.name)
            .setContent(remoteViews).priority = Notification.PRIORITY_MAX

        val next = Intent(Constants.CLICK_NEXT)
        val prev = Intent(Constants.CLICK_PREVIOUS)
        val playpause = Intent(Constants.CLICK_PLAY_PAUSE)

        val nextPendingIntent = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_IMMUTABLE)
        remoteViews.setOnClickPendingIntent(R.id.notnext, nextPendingIntent)
        val prevPendingIntent = PendingIntent.getBroadcast(context, 0, prev, PendingIntent.FLAG_IMMUTABLE)
        remoteViews.setOnClickPendingIntent(R.id.notprev, prevPendingIntent)
        val playPendingIntent = PendingIntent.getBroadcast(context, 0, playpause, PendingIntent.FLAG_IMMUTABLE)
        remoteViews.setOnClickPendingIntent(R.id.notplaypause, playPendingIntent)

        notificationBuilder.setOngoing(true)
        notificationManager.notify(Constants.NOTIFICATION_SONG_ID, notificationBuilder.build())
    }

    fun updateView(isPlaying: Boolean, currentSongPosition: Int) {
        this.isPlaying = isPlaying
        this.currentSongPosition = currentSongPosition

        if (isPlaying) {
            remoteViews.setImageViewResource(R.id.notplaypause, R.drawable.pause)
            notificationBuilder.setOngoing(true)
        } else {
            notificationBuilder.setOngoing(false)
            remoteViews.setImageViewResource(R.id.notplaypause, R.drawable.play)
        }

        notificationBuilder.setContent(remoteViews)
        notificationManager.notify(Constants.NOTIFICATION_SONG_ID, notificationBuilder.build())
    }
}