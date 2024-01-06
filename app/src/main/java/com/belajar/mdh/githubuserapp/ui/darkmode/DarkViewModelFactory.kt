package com.belajar.mdh.githubuserapp.ui.darkmode
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.belajar.mdh.githubuserapp.utils.SettingPreferences
//
//class DarkViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)) {
//            return DarkModeViewModel(pref) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//}