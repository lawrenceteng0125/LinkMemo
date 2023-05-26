package com.example.linkmemo.data

import com.example.linkmemo.data.network.NetworkWordDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordRepository {
    private val networkWordDataSource: NetworkWordDataSource

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://114.80.33.99:3374/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        networkWordDataSource = retrofit.create(NetworkWordDataSource::class.java)
    }

    suspend fun searchWord(english: String): String = networkWordDataSource.getDefinition(english).str

}