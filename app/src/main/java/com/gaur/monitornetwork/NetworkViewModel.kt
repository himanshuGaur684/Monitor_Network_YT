package com.gaur.monitornetwork

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(private val listenNetwork: ListenNetwork): ViewModel() {

    val isConnected : Flow<Boolean> = listenNetwork.isConnected

}