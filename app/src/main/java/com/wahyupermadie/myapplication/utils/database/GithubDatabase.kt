package com.wahyupermadie.myapplication.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wahyupermadie.myapplication.data.dao.UserDao
import com.wahyupermadie.myapplication.data.repository.entity.UserResponse

@Database(entities = [UserResponse::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        fun createInstance(context: Context): GithubDatabase {
            return synchronized(GithubDatabase::class) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "githubdb"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }
}