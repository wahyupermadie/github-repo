package com.wahyupermadie.myapplication.data.usecase

import androidx.paging.PagingData
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.utils.network.State
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {
    suspend fun fetchUser(): Flow<PagingData<User>>
    suspend fun fetchUserDetail(userName: String): State<User>
    suspend fun updateUser(note: String, id: Int)
}