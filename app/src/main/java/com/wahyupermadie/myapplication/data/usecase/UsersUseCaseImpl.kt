package com.wahyupermadie.myapplication.data.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.wahyupermadie.myapplication.data.repository.local.LocalRepository
import com.wahyupermadie.myapplication.data.repository.remote.NetworkRepository
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.utils.extension.transform
import com.wahyupermadie.myapplication.utils.network.State
import com.wahyupermadie.myapplication.utils.network.State.Failure
import com.wahyupermadie.myapplication.utils.network.State.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository
) : UsersUseCase {

    override suspend fun fetchUser(): Flow<PagingData<User>> {
        return networkRepository.fetchUsers().map {
            it.map {
                it.transform()
            }
        }
    }

    override suspend fun fetchUserDetail(userName: String): State<User> {
        return when (val response = networkRepository.getUser(userName)) {
            is Success -> {
                val user = response.data.transform()
                Success(user)
            }
            is Failure -> {
                Failure(response.error)
            }
        }
    }

    override suspend fun updateUser(note: String, id: Int) {
        localRepository.updateUser(note, id)
    }
}