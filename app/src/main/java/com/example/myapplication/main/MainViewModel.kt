package com.example.myapplication.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.ApiClient
import com.example.myapplication.utils.ResultUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val resultUser = MutableLiveData<ResultUtil>()

    fun getUser() {
        viewModelScope.launch {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .getSearch()

                    emit(response)
                }.onStart {
                    resultUser.value = ResultUtil.Loading(true)

                }.onCompletion {
                    resultUser.value = ResultUtil.Loading(false)

                }.catch {
                    Log.e("ERROR", it.message.toString())
                    it.printStackTrace()
                    resultUser.value = ResultUtil.Error(it)

                }.collect{ // mendapatkan response
                    resultUser.value = ResultUtil.Success(it.items)
                }
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .searchUser(mapOf(
                            "q" to username,
                            "per_page" to 5

                        ))

                    emit(response)
                }.onStart {
                    resultUser.value = ResultUtil.Loading(true)

                }.onCompletion {
                    resultUser.value = ResultUtil.Loading(false)

                }.catch {
                    Log.e("ERROR", it.message.toString())
                    it.printStackTrace()
                    resultUser.value = ResultUtil.Error(it)

                }.collect{ // mendapatkan response
                    resultUser.value = ResultUtil.Success(it.items)
                }
            }
        }
    }
}