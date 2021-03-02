package com.wahyupermadie.myapplication.di

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.wahyupermadie.myapplication.BuildConfig
import com.wahyupermadie.myapplication.data.service.ApiService
import com.wahyupermadie.myapplication.utils.network.AppDispatcherProvider
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.NetworkCacheInterceptor
import com.wahyupermadie.myapplication.utils.network.connection.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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
        application: Application,
        networkState: NetworkState
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(application.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(ChuckInterceptor(application))
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build()
                it.proceed(request)
            }.addInterceptor(NetworkCacheInterceptor(networkState))
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