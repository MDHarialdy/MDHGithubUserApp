package com.belajar.mdh.githubuserapp.data.network

import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.data.response.DetailUserResponse
import com.belajar.mdh.githubuserapp.data.response.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("/users")
    suspend fun getUserGithub(): MutableList<GetUserItemResponse>

    @JvmSuppressWildcards
    @GET("/users/{username}")
    suspend fun getDetailUserGithub(
        @Path("username")
        username: String
    ): DetailUserResponse

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowerGithub(
        @Path("username")
        username: String
    ): MutableList<GetUserItemResponse>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingGithub(
        @Path("username")
        username: String
    ): MutableList<GetUserItemResponse>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(
        @QueryMap search: Map<String, Any>
    ): ResponseUserGithub


    }