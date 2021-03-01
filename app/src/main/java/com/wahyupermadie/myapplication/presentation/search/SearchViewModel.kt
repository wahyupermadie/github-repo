package com.wahyupermadie.myapplication.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.data.usecase.search.SearchUserUseCase
import com.wahyupermadie.myapplication.presentation.base.BaseViewModel
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun searchUser(keyword: String) = viewModelScope.launch(dispatcherProvider.ui()) {
        _isLoading.value = Event(true)
        val response = withContext(dispatcherProvider.io()) {
            searchUserUseCase.searchUsers(keyword, keyword)
        }

        response.collectLatest {
            _users.value = it
            _isLoading.value = Event(false)
        }
    }
}