package com.example.myapplication.utils

sealed class ResultUtil {
    data class Success<out T>(val data: T) : ResultUtil()
    data class Error(val exception: Throwable) : ResultUtil()
    data class Loading(val isLoading: Boolean) : ResultUtil()
}