package com.wahyupermadie.myapplication.utils.network

sealed class State<out V> {
    data class Success<out V>(val data: V) : State<V>()
    data class Failure<out V>(
        val error: Throwable? = null,
        val message: String? = null,
        val responseCode: Int? = null
    ) : State<V>()
}


