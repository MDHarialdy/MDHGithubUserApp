package com.belajar.mdh.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.belajar.mdh.githubuserapp.database.FavoriteEntity
import com.belajar.mdh.githubuserapp.repository.AppRepository


class FavoriteViewModel(val appRepository: AppRepository) : ViewModel() {

    // LiveData untuk mengamati status favorit
    fun isFavorite(username: String): LiveData<FavoriteEntity> {
        return appRepository.isFavorite(username)
    }

    // Menambahkan pengguna ke daftar favorit
    fun addToFavorites(favoriteEntity: FavoriteEntity) {
            appRepository.addToFavorites(favoriteEntity)
    }

    // Menghapus pengguna dari daftar favorit
    fun removeFromFavorites(favoriteEntity: FavoriteEntity) {
            appRepository.removeFromFavorites(favoriteEntity)
    }

    //Mendapatkan List User
    fun getAllUser(): LiveData<List<FavoriteEntity>> = appRepository.allFavoriteUsers()

}
