package com.belajar.mdh.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.data.response.DetailUserResponse
import com.belajar.mdh.githubuserapp.data.response.GetUserItemResponse
import com.belajar.mdh.githubuserapp.repository.AppRepository
import kotlinx.coroutines.launch


class DetailViewModel(val appRepository: AppRepository): ViewModel() {

    private var _responseDetail = MutableLiveData<DetailUserResponse>()
    var responseDetail : LiveData<DetailUserResponse> = _responseDetail

    private var _responseFollower = MutableLiveData<MutableList<GetUserItemResponse>>()
    var responseFollower : LiveData<MutableList<GetUserItemResponse>> = _responseFollower

    private var _responseFollowing = MutableLiveData<MutableList<GetUserItemResponse>>()
    var responseFollowing : LiveData<MutableList<GetUserItemResponse>> = _responseFollowing

    fun getDetailUser(username: String){
        viewModelScope.launch {
            val response = appRepository.getDetailUserGithub(username)
            _responseDetail.value = response
        }
    }

    fun getFollower(username: String){
        viewModelScope.launch {
            val response = appRepository.getFollower(username)
            _responseFollower.value = response
        }
    }

    fun getFollowing(username: String){
        viewModelScope.launch {
            val response = appRepository.getFollowing(username)
            _responseFollowing.value = response
        }
    }

}


