package com.wahyupermadie.myapplication.data.datasource.network

import android.util.Log
import androidx.paging.*
import com.wahyupermadie.myapplication.data.datasource.local.UsersLocalDataSource
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.data.service.ApiService
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.State
import com.wahyupermadie.myapplication.utils.network.safeCallApi
import kotlinx.coroutines.*

class UsersDataSource(
    private val apiService: ApiService,
    private val usersLocalDataSource: UsersLocalDataSource,
    private val dispatcherProvider: DispatcherProvider
) : PagingSource<Int, UserResponse>() {

    private fun getLastUserId(data: List<UserResponse>) : Int {
        val length = data.size
        return data[length - 1].id
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        val nextPage = params.key ?: 0

        val response = safeCallApi {
            apiService.fetchUser(nextPage, 20)
        }

        return when (response) {
            is State.Success -> {
                response.data.forEach {
                    val user = it.copy(page = nextPage)
                    usersLocalDataSource.insertUser(user)
                }
                val users = withContext(dispatcherProvider.io()) {
                    usersLocalDataSource.getUsers(nextPage)
                }
                LoadResult.Page(
                    data = users,
                    prevKey = null,
                    nextKey = getLastUserId(response.data)
                )
            }
            is State.Failure -> {
                response.error?.let { LoadResult.Error(it) } ?: LoadResult.Error(Throwable())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        return 0
    }
}