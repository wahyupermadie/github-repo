package com.wahyupermadie.myapplication.data.repository.remote

import androidx.paging.*
import com.wahyupermadie.myapplication.data.datasource.network.UsersDataSourceImpl
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.utils.network.State
import kotlinx.coroutines.flow.Flow

class NetworkRepositoryImpl(
    private val usersDataSourceImpl: UsersDataSourceImpl
) : NetworkRepository {
    override suspend fun fetchUsers(): Flow<PagingData<UserResponse>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {usersDataSourceImpl}
        ).flow
    }

    override suspend fun getUser(userName: String): State<UserResponse> {
        return usersDataSourceImpl.getUser(userName)
    }
}