package com.belajar.mdh.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.data.response.DetailUserResponse
import com.belajar.mdh.githubuserapp.repository.AppRepository
import com.belajar.mdh.githubuserapp.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class DetailViewModel(val appRepository: AppRepository): ViewModel() {

    private var _responseDetail = MutableLiveData<DetailUserResponse>()
    var responseDetail : LiveData<DetailUserResponse> = _responseDetail

//    private var _responseFollower = MutableLiveData<MutableList<GetUserItemResponse>>()
//    val responseFollower : LiveData<MutableList<GetUserItemResponse>> = _responseFollower
//
//    private var _responseFollowing = MutableLiveData<MutableList<GetUserItemResponse>>()
//    val responseFollowing : LiveData<MutableList<GetUserItemResponse>> = _responseFollowing


    val resultFollower = MutableLiveData<ResultData>()
    val resultFollowing = MutableLiveData<ResultData>()

    fun getDetailUser(username: String){
        viewModelScope.launch {
            val response = appRepository.getDetailUserGithub(username)
            _responseDetail.value = response
        }
    }

//    fun getFollower(username: String){
//        viewModelScope.launch {
//            val response = appRepository.getFollower(username)
//            _responseFollower.value = response
//        }
//    }
//
//    fun getFollowing(username: String){
//        viewModelScope.launch {
//            val response = appRepository.getFollowing(username)
//            _responseFollowing.value = response
//        }
//    }


    //fungsi untuk mendapatkan list foolower
    fun getFollower(username: String){
        viewModelScope.launch {
            flow {
                val response = appRepository.getFollower(username)
                emit(response)

            }.onStart {
                resultFollower.value = ResultData.loading(true)
            }.onCompletion {
                resultFollower.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollower.value = ResultData.Error(it)
            }.collect{
                resultFollower.value = ResultData.Succes(it)
            }
        }
    }


    //fungsi untuk mendapatkan list following
    fun getFollowing(username: String){
        viewModelScope.launch {
            flow {
                val response = appRepository.getFollowing(username)
                emit(response)

            }.onStart {
                resultFollowing.value = ResultData.loading(true)
            }.onCompletion {
                resultFollowing.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowing.value = ResultData.Error(it)
            }.collect{
                resultFollowing.value = ResultData.Succes(it)
            }
        }
    }
}


