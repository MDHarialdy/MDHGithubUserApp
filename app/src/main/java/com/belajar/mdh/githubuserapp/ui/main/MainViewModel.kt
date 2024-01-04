package com.belajar.mdh.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.repository.AppRepository
import kotlinx.coroutines.launch

//view model untuk mainactivity
class MainViewModel(val repository: AppRepository): ViewModel() {


    private var _responseUser = MutableLiveData<MutableList<GetUserItemResponse>>()
    var responseUser : LiveData<MutableList<GetUserItemResponse>> = _responseUser
    //fungsi untuk mendapatkan list user
    fun getUser(){
        viewModelScope.launch {
            val response = repository.getUserGithub()
            _responseUser.value = response
        }
    }


    //fungsi untuk mencari user
    fun searchUser(username: String){

    }
}