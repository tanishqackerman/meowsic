package com.meow.meowsic.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent

class MediaButtonBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentAction = intent!!.action
        if (Intent.ACTION_MEDIA_BUTTON != intentAction) {
            return
        }
        val event = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT) ?: return
        val action = event.action
        abortBroadcast()
    }
}