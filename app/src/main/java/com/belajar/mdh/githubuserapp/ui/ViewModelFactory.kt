package com.belajar.mdh.githubuserapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belajar.mdh.githubuserapp.data.injection.Injection
import com.belajar.mdh.githubuserapp.repository.AppRepository
import com.belajar.mdh.githubuserapp.ui.darkmode.DarkModeViewModel
import com.belajar.mdh.githubuserapp.ui.detail.DetailViewModel
import com.belajar.mdh.githubuserapp.ui.favorite.FavoriteViewModel
import com.belajar.mdh.githubuserapp.ui.main.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val appRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(DarkModeViewModel::class.java) -> {
                DarkModeViewModel(appRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {

        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}