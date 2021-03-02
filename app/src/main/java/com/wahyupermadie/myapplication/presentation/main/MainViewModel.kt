package com.wahyupermadie.myapplication.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.presentation.base.BaseViewModel
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.Event
import com.wahyupermadie.myapplication.utils.network.connection.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersUseCase: UsersUseCaseImpl,
    private val dispatcher: DispatcherProvider,
    networkState: NetworkState
) : BaseViewModel(networkState) {

    private val _users = MutableLiveData<PagingData<User>>()
    val users: LiveData<PagingData<User>>
        get() = _users

    fun getUser() = viewModelScope.launch(dispatcher.ui()) {
        _isLoading.value = Event(true)
        try {
            val response = usersUseCase.fetchUser()
            response.collectLatest {
                _users.value = it
                _isLoading.value = Event(false)
            }
            _isLoading.value = Event(false)
        } catch (e: Exception) {
            _error.value = Event(e)
        }
    }
}