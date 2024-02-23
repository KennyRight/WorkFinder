package com.example.workfinder.data.api

import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("vacancies?offset=1&limit=50")
    suspend fun getVacancies(): VacanciesResponse

    @GET("vacancies?offset=1&limit=50")
    suspend fun searchVacancies(@Query("text") query: String): VacanciesResponse
}