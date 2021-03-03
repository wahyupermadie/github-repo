package com.wahyupermadie.myapplication.data.dao

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
    suspend fun getUsers(pageIndex: Int): List<UserResponse>

    @Query("UPDATE tb_user SET followers = :followers, following = :following, publicRepos = :repos, blog = :blog WHERE id = :id")
    suspend fun updateUser(followers: Int, following: Int, repos: Int, blog: String, id: Int)

    @Query("SELECT * FROM tb_user WHERE id = :id")
    suspend fun getUserDetail(id: Int): UserResponse

    @Query("SELECT * FROM tb_user WHERE login LIKE :name OR note LIKE :note")
    fun searchUsers(name: String, note: String): Flow<List<UserResponse>>

    @Query("UPDATE tb_user SET note = :note WHERE id = :id")
    suspend fun updateUser(note: String, id: Int)
}