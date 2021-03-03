package com.wahyupermadie.myapplication.di

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.wahyupermadie.myapplication.BuildConfig
import com.wahyupermadie.myapplication.data.service.ApiService
import com.wahyupermadie.myapplication.utils.network.AppDispatcherProvider
import com.wahyupermadie.myapplication.utils.network.DispatcherProvider
import com.wahyupermadie.myapplication.utils.network.REWRITE_RESPONSE_INTERCEPTOR
import com.wahyupermadie.myapplication.utils.network.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE
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
        application: Application
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(application.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(ChuckInterceptor(application))
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .addInterceptor(application.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
            .cache(myCache)
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