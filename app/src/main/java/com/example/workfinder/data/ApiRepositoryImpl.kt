package com.example.workfinder.data

import com.example.workfinder.data.api.ApiService
import com.example.workfinder.domain.ApiRepository
import com.example.workfinder.domain.models.VacancyDomain

class ApiRepositoryImpl(private val apiService: ApiService): ApiRepository {
    override suspend fun getVacancies(): List<VacancyDomain> {
        return vacancyFromAPIMapper(apiService.getVacancies())
    }

    override suspend fun searchVacancies(query: String): List<VacancyDomain> {
        return vacancyFromAPIMapper(apiService.searchVacancies(query))
    }

}