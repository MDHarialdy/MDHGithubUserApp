package com.belajar.mdh.githubuserapp.data.response

data class DetailUserResponse(
    val avatar_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val login: String,
    val name: String,
    val location: String
)