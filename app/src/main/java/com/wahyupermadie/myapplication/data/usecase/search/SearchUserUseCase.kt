package com.wahyupermadie.myapplication.data.usecase.search

import com.wahyupermadie.myapplication.data.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface SearchUserUseCase {

    suspend fun searchUsers(name: String, note: String): Flow<List<User>>
}