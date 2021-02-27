package com.wahyupermadie.myapplication.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.wahyupermadie.myapplication.data.datasource.network.UsersDataSource
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

class NetworkRepositoryImpl(
    private val usersDataSource: UsersDataSource
) : NetworkRepository {
    override suspend fun fetchUsers(): Flow<PagingData<UserResponse>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {usersDataSource}
        ).flow
    }
}