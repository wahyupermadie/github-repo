package com.wahyupermadie.myapplication.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.presentation.base.BaseViewModel
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersUseCase: UsersUseCaseImpl,
    private val dispatcher: DispatcherProvider
) : BaseViewModel() {

    private val _users = MutableLiveData<PagingData<User>>()
    val users: LiveData<PagingData<User>>
        get() = _users

    fun getUser() = viewModelScope.launch(dispatcher.ui()) {
        val response = withContext(dispatcher.io()) {
            usersUseCase.fetchUser()
        }

        response.collectLatest {
            _users.value = it
        }
    }
}