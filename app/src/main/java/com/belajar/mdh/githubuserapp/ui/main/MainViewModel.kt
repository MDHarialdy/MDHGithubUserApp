package com.belajar.mdh.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

//view model untuk mainactivity
class MainViewModel: ViewModel() {

    val resultData = MutableLiveData<ResultData>()


    //fungsi untuk mendapatkan list user
    fun getUser(){
        viewModelScope.launch {
                flow {
                    val response = com.belajar.mdh.githubuserapp.data.network.ApiClient
                        .githubService
                        .getUserGithub()
                    emit(response)

                }.onStart {
                        resultData.value = ResultData.loading(true)
                }.onCompletion {
                        resultData.value = ResultData.loading(false)
                }.catch {
                      Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultData.value = ResultData.Error(it)
                }.collect{
                    resultData.value = ResultData.Succes(it)
                }
        }
    }


    //fungsi untuk mencari user
    fun searchUser(username: String){
        viewModelScope.launch {
            flow {
                val response = com.belajar.mdh.githubuserapp.data.network.ApiClient
                    .githubService
                    .searchUserGithub(mapOf(
                        "q" to username
                    ))
                emit(response)

            }.onStart {
                resultData.value = ResultData.loading(true)
            }.onCompletion {
                resultData.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultData.value = ResultData.Error(it)
            }.collect{
                resultData.value = ResultData.Succes(it.items)
            }
        }
    }
}