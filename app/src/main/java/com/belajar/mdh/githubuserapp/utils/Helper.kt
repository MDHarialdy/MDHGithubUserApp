package com.belajar.mdh.githubuserapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("ServiceCast")
fun checkConnectivity(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.ACCOUNT_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}