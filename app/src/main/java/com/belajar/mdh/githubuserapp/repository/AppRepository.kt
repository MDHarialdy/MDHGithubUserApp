package com.belajar.mdh.githubuserapp.repository

import androidx.lifecycle.LiveData
import com.belajar.mdh.githubuserapp.data.network.ApiService
import com.belajar.mdh.githubuserapp.data.response.DetailUserResponse
import com.belajar.mdh.githubuserapp.database.AppDao
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AppRepository(val appDao: AppDao, val apiService: ApiService) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    // Mendapatkan daftar semua pengguna favorit
   fun allFavoriteUsers() : LiveData<List<FavoriteEntity>> = appDao.getAllUser()

    // Memeriksa apakah seorang pengguna adalah favorit
    fun isFavorite(username: String): LiveData<FavoriteEntity> {
        return appDao.getFavoriteUserByUsername(username)
    }

    // Menambahkan pengguna ke daftar favorit
    fun addToFavorites(favoriteEntity: FavoriteEntity) {
       executorService.execute{appDao.insert(favoriteEntity)}
    }

    // Menghapus pengguna dari daftar favorit
    fun removeFromFavorites(favoriteEntity: FavoriteEntity) {
       executorService.execute{appDao.delete(favoriteEntity)}
    }
    suspend fun getUserGithub() = apiService.getUserGithub()
    suspend fun getDetailUserGithub(username: String): DetailUserResponse {
       return apiService.getDetailUserGithub(username)
    }

    suspend fun getFollower(username: String) = apiService.getFollowerGithub(username)
    suspend fun getFollowing(username: String) = apiService.getFollowingGithub(username)


    companion object {
        fun clearInstance(){
            instance = null
        }
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            appDao: AppDao,
            apiService: ApiService
        ): AppRepository =
            instance?: synchronized(this) {
                instance?: AppRepository(appDao, apiService)
            }.also { instance = it }
    }
}