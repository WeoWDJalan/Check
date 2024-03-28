package com.example.myapplication.detail

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

class DetailViewModel: ViewModel() {
    val resultDetailUser = MutableLiveData<ResultUtil>()
    val resultFollowers = MutableLiveData<ResultUtil>()
    val resultFollowing = MutableLiveData<ResultUtil>()

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .getDetailUser(username)

                    emit(response)
                }.onStart {
                    resultDetailUser.value = ResultUtil.Loading(true)

                }.onCompletion {
                    resultDetailUser.value = ResultUtil.Loading(false)

                }.catch {
                    Log.e("ERROR", it.message.toString())
                    it.printStackTrace()
                    resultDetailUser.value = ResultUtil.Error(it)

                }.collect{ // mendapatkan response
                    resultDetailUser.value = ResultUtil.Success(it)
                }
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .getFollowing(username)

                    emit(response)
                }.onStart {
                    resultFollowers.value = ResultUtil.Loading(true)

                }.onCompletion {
                    resultFollowers.value = ResultUtil.Loading(false)

                }.catch {
                    Log.e("ERROR", it.message.toString())
                    it.printStackTrace()
                    resultFollowers.value = ResultUtil.Error(it)

                }.collect{ // mendapatkan response
                    resultFollowers.value = ResultUtil.Success(it)
                }
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .getFollowers(username)

                    emit(response)
                }.onStart {
                    resultFollowing.value = ResultUtil.Loading(true)

                }.onCompletion {
                    resultFollowing.value = ResultUtil.Loading(false)

                }.catch {
                    Log.e("ERROR", it.message.toString())
                    it.printStackTrace()
                    resultFollowing.value = ResultUtil.Error(it)

                }.collect{ // mendapatkan response
                    resultFollowing.value = ResultUtil.Success(it)
                }
            }
        }
    }
}