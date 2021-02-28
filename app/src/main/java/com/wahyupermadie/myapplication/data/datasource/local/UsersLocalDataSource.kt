package com.wahyupermadie.myapplication.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Query
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.data.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {
    suspend fun insertUser(userResponse: UserResponse)
    suspend fun getUsers(page: Int): List<UserResponse>
    suspend fun updateUser(followers: Int, following: Int, repos: Int, id: Int)
    suspend fun getDetailUser(id: Int): UserResponse
}