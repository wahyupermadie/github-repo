package com.wahyupermadie.myapplication.data.repository.local

import com.wahyupermadie.myapplication.data.datasource.local.UsersLocalDataSource
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val localDataSource: UsersLocalDataSource
) : LocalRepository {

    override suspend fun insertUser(user: UserResponse) {
        localDataSource.insertUser(user)
    }

    override suspend fun getUsers(page: Int): List<UserResponse> {
        return localDataSource.getUsers(page)
    }

    override suspend fun searchUsers(name: String, note: String): Flow<List<UserResponse>> {
        return localDataSource.searchUsers(name, note)
    }
}