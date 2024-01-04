package com.belajar.mdh.githubuserapp.data.network

import com.belajar.mdhgithubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            val authInterceptor = Interceptor { user ->
                val requestUser = user.request()
                val requestHeaders = requestUser.newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .build()
                user.proceed(requestHeaders)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)

        }.readTimeout(25,TimeUnit.SECONDS)
         .writeTimeout(300, TimeUnit.SECONDS)
         .connectTimeout(60, TimeUnit.SECONDS)
         .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val ApiService = retrofit.create<ApiService>()
}

