package com.belajar.mdh.githubuserapp.ui.darkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.repository.AppRepository
import kotlinx.coroutines.launch

class DarkModeViewModel (val appRepository: AppRepository) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return appRepository.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            appRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}