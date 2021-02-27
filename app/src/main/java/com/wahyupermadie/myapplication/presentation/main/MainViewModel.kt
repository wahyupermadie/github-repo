package com.wahyupermadie.myapplication.presentation.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.viewpager.widget.PagerAdapter
import com.wahyupermadie.myapplication.data.datasource.local.UsersLocalDataSourceImpl
import com.wahyupermadie.myapplication.data.usecase.UsersUseCase
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.utils.network.AppDispatcherProvider
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersUseCase: UsersUseCaseImpl,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _users =  MutableLiveData<PagingData<User>>()
    val users : LiveData<PagingData<User>>
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