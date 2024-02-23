package com.example.workfinder.domain

import com.example.workfinder.domain.models.VacancyDomain

interface ApiRepository {
    suspend fun getVacancies(): List<VacancyDomain>

    suspend fun searchVacancies(query: String): List<VacancyDomain>
}