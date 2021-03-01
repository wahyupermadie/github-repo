package com.wahyupermadie.myapplication.data.datasource.local

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

    override suspend fun updateUser(followers: Int, following: Int, repos: Int, blog: String, id: Int) {
        userDao.updateUser(followers, following, repos, blog, id)
    }

    override suspend fun getDetailUser(id: Int): UserResponse {
        return userDao.getUserDetail(id)
    }

    override suspend fun searchUsers(name: String, note: String): Flow<List<UserResponse>> {
        return userDao.searchUsers("%${name}%", "%${note}%")
    }
}