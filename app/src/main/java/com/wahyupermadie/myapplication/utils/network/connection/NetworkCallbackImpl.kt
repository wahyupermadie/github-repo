package com.wahyupermadie.myapplication.utils.network.connection

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import okhttp3.internal.platform.Jdk9Platform.Companion.isAvailable

internal class NetworkCallbackImpl(private val holder: NetworkState) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        holder.network = network
        holder.isConnected = isAvailable
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        holder.networkCapabilities = networkCapabilities
    }

    override fun onLost(network: Network) {
        holder.network = network
        holder.isConnected = false
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        holder.linkProperties = linkProperties
    }
}