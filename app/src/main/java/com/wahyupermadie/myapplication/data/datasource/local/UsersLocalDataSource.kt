package com.wahyupermadie.myapplication.data.datasource.local

import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {
    suspend fun insertUser(userResponse: UserResponse)
    suspend fun getUsers(page: Int): List<UserResponse>
    suspend fun updateUser(followers: Int, following: Int, repos: Int, blog: String, id: Int)
    suspend fun getDetailUser(id: Int): UserResponse
    suspend fun searchUsers(name: String, note: String): Flow<List<UserResponse>>
}