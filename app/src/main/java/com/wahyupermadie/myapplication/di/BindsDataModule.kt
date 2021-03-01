package com.wahyupermadie.myapplication.di

import com.wahyupermadie.myapplication.data.usecase.UsersUseCase
import com.wahyupermadie.myapplication.data.usecase.UsersUseCaseImpl
import com.wahyupermadie.myapplication.data.usecase.search.SearchUserUseCase
import com.wahyupermadie.myapplication.data.usecase.search.SearchUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsDataModule {

    @Binds
    @Singleton
    abstract fun provideUserUseCase(
        usersUseCaseImpl: UsersUseCaseImpl,
    ): UsersUseCase

    @Binds
    @Singleton
    abstract fun provideSearchUseCase(
        searchUserUseCaseImpl: SearchUserUseCaseImpl
    ): SearchUserUseCase
}