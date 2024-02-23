package com.example.workfinder.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Instance {
    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://opendata.trudvsem.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}