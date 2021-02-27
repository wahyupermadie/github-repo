package com.wahyupermadie.myapplication.data.repository.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.data.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertUser(user: UserResponse)
    suspend fun getUsers(page: Int): List<UserResponse>
}