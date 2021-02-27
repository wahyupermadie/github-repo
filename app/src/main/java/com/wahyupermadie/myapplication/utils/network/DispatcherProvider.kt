package com.wahyupermadie.myapplication.utils.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}