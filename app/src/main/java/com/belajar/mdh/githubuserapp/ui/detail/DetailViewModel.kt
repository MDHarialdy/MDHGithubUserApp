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
import retrofit2.HttpException


class DetailViewModel(private val appRepository: AppRepository): ViewModel() {

    private var _responseDetail = MutableLiveData<DetailUserResponse>()
    var responseDetail : LiveData<DetailUserResponse> = _responseDetail

    val resultFollower = MutableLiveData<ResultData>()
    val resultFollowing = MutableLiveData<ResultData>()

    fun getDetailUser(username: String, onError: (String) -> Unit){
        viewModelScope.launch {
            try {
                val response = appRepository.getDetailUserGithub(username)
                _responseDetail.value = response
            } catch (e: HttpException){
                Log.d("Detail View Model :", e.message().toString())
                onError("Error: ${e.message.toString()}")
            }
        }
    }

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


