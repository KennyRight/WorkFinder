package com.example.workfinder.api

import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("vacancies?offset=1&limit=50")
    fun getVacancies(): Call<VacanciesResponse>

    @GET("vacancies")
    fun searchVacancies(@Query("text") query: String): Call<VacanciesResponse>
}