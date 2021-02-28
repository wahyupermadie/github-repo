package com.wahyupermadie.myapplication.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.presentation.base.BaseViewModel
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
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
): BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    suspend fun fetchDetailUser(userName: String) {
        viewModelScope.launch(dispatcher.ui()) {
            val response = withContext(dispatcher.io()) {
                usersUseCase.fetchUserDetail(userName)
            }
            when(response) {
                is Success -> {
                    _user.value = response.data
                }
                is Failure -> {

                }
            }
        }
    }
}