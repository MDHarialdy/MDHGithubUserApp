package com.belajar.mdh.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdh.githubuserapp.repository.FavoriteRepository


class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    // LiveData untuk mengamati status favorit
    fun isFavorite(username: String): LiveData<FavoriteEntity> {
        return mFavoriteRepository.isFavorite(username)
    }

    // Menambahkan pengguna ke daftar favorit
    fun addToFavorites(favoriteEntity: FavoriteEntity) {
            mFavoriteRepository.addToFavorites(favoriteEntity)
    }

    // Menghapus pengguna dari daftar favorit
    fun removeFromFavorites(favoriteEntity: FavoriteEntity) {
            mFavoriteRepository.removeFromFavorites(favoriteEntity)
    }

    //Mendapatkan List User
    fun getAllUser(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.allFavoriteUsers()

}
