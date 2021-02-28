package com.wahyupermadie.myapplication.data.datasource.network

import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.utils.network.State
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun getUser(userName: String): State<UserResponse>
}