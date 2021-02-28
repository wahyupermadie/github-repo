package com.wahyupermadie.myapplication.data.service

import com.wahyupermadie.myapplication.data.repository.entity.UserResponse
import com.wahyupermadie.myapplication.utils.network.State
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun fetchUser(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<UserResponse>>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") userName: String
    ) : Response<UserResponse>

}
