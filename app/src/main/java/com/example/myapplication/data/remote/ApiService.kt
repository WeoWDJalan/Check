package com.example.myapplication.data.remote

import com.example.myapplication.data.model.DetailUserResponse
import com.example.myapplication.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("search/users?q=Muhammad%20Nabhan")
    suspend fun getSearch(): SearchResponse
    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username")username:String): DetailUserResponse

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username")username:String):  MutableList<SearchResponse.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username")username:String): MutableList<SearchResponse.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUser(@QueryMap params: Map<String, Any>): SearchResponse

}