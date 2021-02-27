package com.wahyupermadie.myapplication.data.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.paging.map
import com.wahyupermadie.myapplication.data.repository.local.LocalRepository
import com.wahyupermadie.myapplication.data.repository.remote.NetworkRepository
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.utils.extension.transform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import okhttp3.internal.wait

class UsersUseCaseImpl(
    private val networkRepository: NetworkRepository,
) : UsersUseCase {
    override suspend fun fetchUser(): Flow<PagingData<User>> {
        return networkRepository.fetchUsers().map {
            it.map {
                it.transform()
            }
        }
    }
}