package com.wahyupermadie.myapplication.utils.network.connection

import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

interface NetworkState {

    var isConnected: Boolean
    var network: Network?
    var networkCapabilities: NetworkCapabilities?
    var linkProperties: LinkProperties?
}

internal class NetworkStateImp : NetworkState {

    override var isConnected: Boolean = false
    override var network: Network? = null
    override var linkProperties: LinkProperties? = null
    override var networkCapabilities: NetworkCapabilities? = null
}