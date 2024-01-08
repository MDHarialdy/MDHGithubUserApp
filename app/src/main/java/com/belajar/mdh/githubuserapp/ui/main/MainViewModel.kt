package com.belajar.mdh.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.mdh.githubuserapp.repository.AppRepository
import com.belajar.mdh.githubuserapp.utils.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

//view model untuk mainactivity
class MainViewModel(val appRepository: AppRepository): ViewModel() {


    val _response = MutableLiveData<ResultData>()
    val response : LiveData<ResultData> = _response

    //get user
    //fungsi untuk mendapatkan list user
    fun getUser(onError: (String) -> Unit){
        viewModelScope.launch {
            flow {
                val response = appRepository.getUserGithub()
                emit(response)

            }.onStart {
                _response.value = ResultData.loading(true)
            }.onCompletion {
                _response.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                _response.value = ResultData.Error(it)
            }.collect{
                _response.value = ResultData.Succes(it)
            }
        }
    }


    //fungsi untuk mencari user
    fun searchUser(q: String){
        viewModelScope.launch {
            flow {
                val response = appRepository.searchUser(q)
                emit(response)

            }.onStart {
                _response.value = ResultData.loading(true)
            }.onCompletion {
                _response.value = ResultData.loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                _response.value = ResultData.Error(it)
            }.collect{
                _response.value = ResultData.Succes(it.items)
            }
        }
    }

}