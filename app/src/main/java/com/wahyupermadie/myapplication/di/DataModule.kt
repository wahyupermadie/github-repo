package com.wahyupermadie.myapplication.di

import android.app.Application
import androidx.room.RoomDatabase
import com.wahyupermadie.myapplication.data.dao.UserDao
import com.wahyupermadie.myapplication.data.datasource.local.UsersLocalDataSource
import com.wahyupermadie.myapplication.data.datasource.local.UsersLocalDataSourceImpl
import com.wahyupermadie.myapplication.data.datasource.network.UsersDataSource
import com.wahyupermadie.myapplication.data.repository.local.LocalRepository
import com.wahyupermadie.myapplication.data.repository.local.LocalRepositoryImpl
import com.wahyupermadie.myapplication.data.repository.remote.NetworkRepository
import com.wahyupermadie.myapplication.data.repository.remote.NetworkRepositoryImpl
import com.wahyupermadie.myapplication.data.service.ApiService
import com.wahyupermadie.myapplication.data.usecase.UsersUseCase
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.utils.database.GithubDatabase
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserUseCase(
        networkRepository: NetworkRepository,
    ): UsersUseCaseImpl = UsersUseCaseImpl(networkRepository)

    @Provides
    @Singleton
    fun provideUserDataSource(
        apiService: ApiService,
        userLocalDataSource: UsersLocalDataSource,
        dispatcherProvider: DispatcherProvider
    ) : UsersDataSource {
        return UsersDataSource(
            apiService,
            userLocalDataSource,
            dispatcherProvider
        )
    }

    @Provides
    @Singleton
    fun provideUserDatabase(
        application: Application
    ): GithubDatabase = GithubDatabase.createInstance(application)

    @Provides
    @Singleton
    fun provideUserDao(
        githubDatabase: GithubDatabase
    ): UserDao = githubDatabase.userDao()

    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        userDao: UserDao
    ): UsersLocalDataSource = UsersLocalDataSourceImpl(userDao)

    @Provides
    @Singleton
    fun provideLocalRepository(
        userLocalDataSource: UsersLocalDataSource
    ): LocalRepository = LocalRepositoryImpl(userLocalDataSource)

    @Provides
    @Singleton
    fun provideNetworkRepository(usersDataSource: UsersDataSource): NetworkRepository {
        return NetworkRepositoryImpl(usersDataSource)
    }
}