package com.example.linkmemo.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkWordDataSource {
    @GET("find")
    suspend fun getDefinition(@Query("english") word: String):NetworkResponse
}