package com.wahyupermadie.myapplication.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyupermadie.myapplication.utils.network.Event

abstract class BaseViewModel : ViewModel() {

    protected val _error =  MutableLiveData<Event<Throwable>>()
    val error : LiveData<Event<Throwable>>
        get() = _error

    protected val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading : LiveData<Event<Boolean>>
        get() = _isLoading

}