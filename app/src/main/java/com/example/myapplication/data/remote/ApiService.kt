package com.example.myapplication.data.remote

import com.example.myapplication.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("users?q=Muhammad%20Nabhan")
    suspend fun getSearch(): MutableList<SearchResponse.Item>
}