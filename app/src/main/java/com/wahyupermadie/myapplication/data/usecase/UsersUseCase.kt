package com.wahyupermadie.myapplication.data.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wahyupermadie.myapplication.data.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {
    suspend fun fetchUser(): Flow<PagingData<User>>
}