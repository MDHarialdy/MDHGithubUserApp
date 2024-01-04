package com.belajar.mdh.githubuserapp.data.response

data class ResponseDetailUser(
    val avatar_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val id: Int,
    val login: String,
    val name: String,
    val url: String
)