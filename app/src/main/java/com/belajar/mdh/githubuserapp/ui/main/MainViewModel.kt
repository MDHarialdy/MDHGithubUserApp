package com.belajar.mdh.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.repository.AppRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

//view model untuk mainactivity
class MainViewModel(val repository: AppRepository): ViewModel() {


    private var _responseUser = MutableLiveData<MutableList<GetUserItemResponse>>()
    var responseUser : LiveData<MutableList<GetUserItemResponse>> = _responseUser
    //fungsi untuk mendapatkan list user
    fun getUser(onError: (String) -> Unit){
        try {
            viewModelScope.launch {
                val response = repository.getUserGithub()
                _responseUser.value = response
            }
        } catch (e: HttpException) {
            Log.d("Main View Model", e.message().toString())
            onError("Error Message: ${e.message.toString()}")
        }
    }


    //fungsi untuk mencari user
    fun searchUser(username: String){

    }
}