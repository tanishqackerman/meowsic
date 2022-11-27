package com.meow.meowsic.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.utilities.SharedPreference


class ExoPlayerService : Service() {

    private lateinit var player: SimpleExoPlayer
    private lateinit var context: Context
    private lateinit var mediaSession: MediaSessionCompat
//    private lateinit var mediaSessionConnector: MediaSessionConnector
    private lateinit var pref: SharedPreference

    private lateinit var currentPlaylist: Playlists
    private var currentPosition = 0

    override fun onCreate() {
        super.onCreate()

        context = this
        pref = SharedPreference(context)
        currentPlaylist = pref.getCurrentPlaylist()
        currentPosition = pref.getCurrentPlayingSongPosition()

//        val concatenatingMediaSource: ConcatenatingMediaSource = ConcatenatingMediaSource()
//        val defaultRenderersFactory: DefaultRenderersFactory = DefaultRenderersFactory(applicationContext)
////        val bandwidthMeter = DefaultBandwidthMeter()
//        val trackSelectionFactory: AdaptiveTrackSelection.Factory = AdaptiveTrackSelection.Factory()
//        val trackSelector: DefaultTrackSelector = DefaultTrackSelector(trackSelectionFactory)
//        val loadControl = DefaultLoadControl()
//
//        player = ExoPlayerFactory.newSimpleInstance(renderedFactory, trackSelector, loadControl)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}