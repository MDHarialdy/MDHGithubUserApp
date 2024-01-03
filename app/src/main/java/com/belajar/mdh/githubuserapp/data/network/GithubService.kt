package com.belajar.mdh.githubuserapp.data.network

import com.belajar.mdh.githubuserapp.data.model.Item
import com.belajar.mdh.githubuserapp.data.model.ResponseDetailUser
import com.belajar.mdh.githubuserapp.data.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GithubService {

    @JvmSuppressWildcards
    @GET("/users")
    suspend fun getUserGithub(): MutableList<Item>

    @JvmSuppressWildcards
    @GET("/users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username: String): ResponseDetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowerGithub(@Path("username") username: String): MutableList<Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingGithub(@Path("username") username: String): MutableList<Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(@QueryMap search: Map<String, Any>): ResponseUserGithub


    }