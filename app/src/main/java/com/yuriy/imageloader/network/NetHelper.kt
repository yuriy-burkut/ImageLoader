package com.yuriy.imageloader.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetHelper(private val context: Context) {

    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkInfo != null && networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}