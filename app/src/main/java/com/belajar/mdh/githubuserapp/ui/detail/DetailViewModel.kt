package com.belajar.mdh.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.data.network.ApiClient
import com.belajar.mdh.githubuserapp.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


//view model untuk DetailActivity
class DetailViewModel: ViewModel() {

    //inisiasi variable result
    val resultDetailUser = MutableLiveData<ResultData>()
    val resultFollower = MutableLiveData<ResultData>()
    val resultFollowing = MutableLiveData<ResultData>()
    val followerCount = MutableLiveData<Int>()
    val followingCount = MutableLiveData<Int>()


    //fungsi untuk mendapatkan data detail user yang dikirim
    fun getDetailUser(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .ApiService
                    .getDetailUserGithub(username)
                emit(response)

            }.onStart {
                resultDetailUser.value = ResultData.loading(true)
            }.onCompletion {
                resultDetailUser.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = ResultData.Error(it)
            }.collect{
                resultDetailUser.value = ResultData.Succes(it)
            }
        }
    }


    //fungsi untuk mendapatkan list foolower
    fun getFollower(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .ApiService
                    .getFollowerGithub(username)
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

                // Hitung total jumlah pengikut
                val followersList = it
                val totalFollowers = followersList.size
                followerCount.value = totalFollowers
            }
        }
    }


    //fungsi untuk mendapatkan list following
    fun getFollowing(username: String){
        viewModelScope.launch {
            flow {
                val response = com.belajar.mdh.githubuserapp.data.network.ApiClient
                    .ApiService
                    .getFollowingGithub(username)
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

                // Hitung total jumlah pengikut
                val followingList = it
                val totalFollowing = followingList.size
                followingCount.value = totalFollowing
            }
        }
    }
}