package com.wahyupermadie.myapplication.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun fetchUsers() : Flow<PagingData<UserResponse>>
}