package com.wahyupermadie.myapplication.data.repository.local

import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertUser(user: UserResponse)
    suspend fun getUsers(page: Int): List<UserResponse>
    suspend fun searchUsers(name: String, note: String): Flow<List<UserResponse>>
}