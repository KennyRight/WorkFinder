package com.example.workfinder.domain.interactors

import com.example.workfinder.domain.ApiRepository
import com.example.workfinder.domain.models.VacancyDomain

class GetVacanciesUseCase(private val repository: ApiRepository) {
    suspend fun execute(): List<VacancyDomain> {
        return repository.getVacancies()
    }
}