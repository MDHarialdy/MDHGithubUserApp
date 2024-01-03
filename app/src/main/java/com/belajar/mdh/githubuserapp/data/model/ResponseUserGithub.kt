package com.belajar.mdh.githubuserapp.data.model

data class ResponseUserGithub(
    val incomplete_results: Boolean,
    val items: MutableList<Item>,
    val total_count: Int
)