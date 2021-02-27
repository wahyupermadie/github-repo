package com.wahyupermadie.myapplication.data.datasource.local

import androidx.paging.PagingSource
import com.wahyupermadie.myapplication.data.dao.UserDao
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

class UsersLocalDataSourceImpl(
    private val userDao: UserDao
) : UsersLocalDataSource {
    override suspend fun insertUser(userResponse: UserResponse) {
        userDao.insertUser(userResponse)
    }

    override suspend fun getUsers(page: Int): List<UserResponse> {
        return userDao.getUsers(pageIndex = page)
    }
}