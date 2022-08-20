package com.gaur.monitornetwork

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object MonitorConnectivity {


    private val IMPL = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
        Marshmellow
    }else{
        BaseImpl
    }

    fun isConnected(connectivityManager: ConnectivityManager):Boolean{
           return IMPL.isConnected(connectivityManager)
    }


}


interface ConnectedCompat {
    fun isConnected(connectivityManager: ConnectivityManager): Boolean
}

object Marshmellow : ConnectedCompat {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun isConnected(connectivityManager: ConnectivityManager): Boolean {
       return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(
           NetworkCapabilities.NET_CAPABILITY_INTERNET
       ) == true
    }
}

object BaseImpl : ConnectedCompat {
    override fun isConnected(connectivityManager: ConnectivityManager): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}

