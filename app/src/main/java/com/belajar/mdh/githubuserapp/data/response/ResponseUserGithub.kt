package com.belajar.mdh.githubuserapp.data.response

data class ResponseUserGithub(
    val incomplete_results: Boolean,
    val items: MutableList<GetUserResponseItem>,
    val total_count: Int
)