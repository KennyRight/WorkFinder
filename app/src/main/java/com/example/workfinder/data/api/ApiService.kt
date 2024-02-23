package com.example.workfinder.data.api

import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("vacancies?offset=1&limit=50")
    fun getVacancies(): VacanciesResponse

    @GET("vacancies?offset=1&limit=50")
    fun searchVacancies(@Query("text") query: String): VacanciesResponse
}