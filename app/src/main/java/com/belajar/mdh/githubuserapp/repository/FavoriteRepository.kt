package com.belajar.mdh.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.belajar.mdh.githubuserapp.database.FavoriteDao
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdh.githubuserapp.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(application: Application) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mFavoriteDao: FavoriteDao
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    // Mendapatkan daftar semua pengguna favorit
   fun allFavoriteUsers() : LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllUser()

    // Memeriksa apakah seorang pengguna adalah favorit
    fun isFavorite(username: String): LiveData<FavoriteEntity> {
        return mFavoriteDao.getFavoriteUserByUsername(username)
    }

    // Menambahkan pengguna ke daftar favorit
    fun addToFavorites(favoriteEntity: FavoriteEntity) {
       executorService.execute{mFavoriteDao.insert(favoriteEntity)}
    }

    // Menghapus pengguna dari daftar favorit
    fun removeFromFavorites(favoriteEntity: FavoriteEntity) {
       executorService.execute{mFavoriteDao.delete(favoriteEntity)}
    }
}