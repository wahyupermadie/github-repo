package com.wahyupermadie.myapplication.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userResponse: UserResponse)

    @Query("SELECT * FROM tb_user WHERE page = :pageIndex")
    fun getUsers(pageIndex: Int): List<UserResponse>
}