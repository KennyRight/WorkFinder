package com.example.workfinder.domain.interactors

import com.example.workfinder.domain.DatabaseRepository
import com.example.workfinder.domain.models.VacancyDomain

class GetSavedVacanciesUseCase(private val repository: DatabaseRepository) {
    fun execute(): List<VacancyDomain> {
        return repository.getAllVacancies()
    }
}