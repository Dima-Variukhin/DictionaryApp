package com.myapp.dictionaryapp.core

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface ConnectionManager {
    fun isNetWorkAvailable(): Boolean

    open class Base(private val connectivityManager: ConnectivityManager?) : ConnectionManager {
        override fun isNetWorkAvailable(): Boolean {
            val result: Boolean?
            val networkCapabilities = connectivityManager?.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
        }
    }
}