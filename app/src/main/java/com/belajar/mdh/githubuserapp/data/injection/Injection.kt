package com.belajar.mdh.githubuserapp.data.injection

import android.content.Context
import com.belajar.mdh.githubuserapp.data.network.ApiClient
import com.belajar.mdh.githubuserapp.database.AppDatabase
import com.belajar.mdh.githubuserapp.repository.AppRepository
import com.belajar.mdh.githubuserapp.utils.SettingPreferences
import com.belajar.mdh.githubuserapp.utils.dataStore
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AppRepository = runBlocking{
        val appDatabase = AppDatabase.getDatabase(context)
        val apiService = ApiClient.ApiService
        val pref = SettingPreferences.getInstance(context.dataStore)
        AppRepository.getInstance(appDatabase.appDao(), apiService, pref)
    }
}