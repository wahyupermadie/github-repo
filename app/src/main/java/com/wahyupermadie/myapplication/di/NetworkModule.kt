package com.wahyupermadie.myapplication.di

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.wahyupermadie.myapplication.BuildConfig
import com.wahyupermadie.myapplication.data.service.ApiService
import com.wahyupermadie.myapplication.utils.network.AppDispatcherProvider
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAppDispatcherProvider(): DispatcherProvider {
        return AppDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provdieOkHttpClinet(
        application: Application
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(application))
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Authorization","token 7f37e6642f291050926ddecf1a304bb9b348b382")
                    .addHeader("Accept","application/vnd.github.v3+json")
                    .build()
                it.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}