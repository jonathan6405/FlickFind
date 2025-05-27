package com.example.moviedb2025.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

fun observeConnectivity(context: Context): Flow<ConnectionState> = callbackFlow {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(ConnectionState.Available)
        }
        override fun onLost(network: Network) {
            trySend(ConnectionState.Unavailable)
        }
    }
    //Register callback
    connectivityManager.registerDefaultNetworkCallback(callback)

    //Initial state check
    val isConnected = connectivityManager.activeNetwork?.let {
        connectivityManager.getNetworkCapabilities(it)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } == true
    trySend(if (isConnected) ConnectionState.Available else ConnectionState.Unavailable)

    awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
}
