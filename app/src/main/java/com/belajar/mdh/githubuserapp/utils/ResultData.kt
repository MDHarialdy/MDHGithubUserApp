package com.belajar.mdh.githubuserapp.utils

sealed class ResultData{
    data class Succes<out T>(val data: T): ResultData()
    data class Error(val exception: Throwable): ResultData()
    data class loading(val isLoading: Boolean): ResultData()
}
