package com.meow.meowsic.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

class InternetConnection {

    fun isOnline(context: Context?): Boolean {
        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo

        if (networkInfo != null) {
            val wifi: NetworkInfo? = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile: NetworkInfo? = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())
        }
        return false
    }
}