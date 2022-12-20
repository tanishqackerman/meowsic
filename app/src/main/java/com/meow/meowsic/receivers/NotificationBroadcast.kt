package com.meow.meowsic.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.meow.meowsic.activities.MainActivity
import com.meow.meowsic.services.MusicService
import com.meow.meowsic.utilities.Constants

class NotificationBroadcast : BroadcastReceiver() {

    val Constants = Constants()
    val MainActivity = MainActivity()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == Constants.CLICK_NEXT) {
            val musicService: MusicService = MainActivity.musicSrv
            musicService.playNext()
        } else if (intent.action == Constants.CLICK_PLAY_PAUSE) {
            val musicService: MusicService = MainActivity.musicSrv
            if (musicService.getState() != Constants.MUSIC_LOADED) {
                if (musicService.isPlaying()) musicService.pausePlayer() else musicService.go()
            }
        } else if (intent.action == Constants.CLICK_PREVIOUS) {
            val musicService: MusicService = MainActivity.musicSrv
            musicService.playPrev()
        }
    }
}