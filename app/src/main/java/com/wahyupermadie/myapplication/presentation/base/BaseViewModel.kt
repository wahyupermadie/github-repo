package com.wahyupermadie.myapplication.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyupermadie.myapplication.utils.network.Event
import com.wahyupermadie.myapplication.utils.network.connection.NetworkState

abstract class BaseViewModel(
    private val networkState: NetworkState
) : ViewModel() {

    protected val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>>
        get() = _error

    protected val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    protected val _isNetworkAvailable = MutableLiveData<Event<Boolean>>()
    val isNetworkAvailable: LiveData<Event<Boolean>>
        get() = _isNetworkAvailable

    fun checkConnection() {
        _isNetworkAvailable.value = Event(networkState.isConnected)
    }
}