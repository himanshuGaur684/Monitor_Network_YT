package com.gaur.monitornetwork

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ListenNetwork @Inject constructor(connectivityManager: ConnectivityManager) {

    val isConnected = callbackFlow<Boolean> {

        val callback = object: ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }

        val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .apply {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                }
            }.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        trySend(MonitorConnectivity.isConnected(connectivityManager))
        connectivityManager.registerNetworkCallback(request,callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }


}