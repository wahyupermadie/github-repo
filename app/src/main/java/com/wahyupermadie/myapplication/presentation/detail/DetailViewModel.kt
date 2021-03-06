package com.wahyupermadie.myapplication.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.presentation.base.BaseViewModel
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.Event
import com.wahyupermadie.myapplication.utils.network.State.Failure
import com.wahyupermadie.myapplication.utils.network.State.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val usersUseCase: UsersUseCaseImpl,
    private val dispatcher: DispatcherProvider
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isUpdateSuccess = MutableLiveData<Event<Boolean>>()
    val isUpdateSuccess: LiveData<Event<Boolean>>
        get() = _isUpdateSuccess

    suspend fun fetchDetailUser(userName: String) {
        _isLoading.value = Event(true)
        viewModelScope.launch(dispatcher.ui()) {
            val response = withContext(dispatcher.io()) {
                usersUseCase.fetchUserDetail(userName)
            }
            when (response) {
                is Success -> {
                    _user.value = response.data
                    _isLoading.value = Event(false)
                }
                is Failure -> {
                    _error.value = Event(response.error!!)
                }
            }
        }
    }

    suspend fun updateUserNote(note: String, id: Int) {
        viewModelScope.launch(dispatcher.ui()) {
            try {
                usersUseCase.updateUser(note, id)
                _isUpdateSuccess.value = Event(true)
            } catch (e: Exception) {
                _isUpdateSuccess.value = Event(false)
            }
        }
    }
}