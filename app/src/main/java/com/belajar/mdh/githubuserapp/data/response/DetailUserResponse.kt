package com.belajar.mdh.githubuserapp.data.response

data class DetailUserResponse(
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
    val location: String
)