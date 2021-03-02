package com.wahyupermadie.myapplication.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.wahyupermadie.myapplication.utils.network.connection.NetworkCallbackImpl
import com.wahyupermadie.myapplication.utils.network.connection.NetworkState
import com.wahyupermadie.myapplication.utils.network.connection.NetworkStateImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectionModule {

    @Provides
    @Singleton
    fun provideNetworkCallBackImpl(
        application: Application
    ): NetworkState {
        val networkState = NetworkStateImp()
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            NetworkCallbackImpl(networkState)
        )
        return networkState
    }
}