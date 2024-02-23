package com.example.workfinder.domain.interactors

import com.example.workfinder.domain.DatabaseRepository
import com.example.workfinder.domain.models.VacancyDomain

class SaveVacancyUseCase(private val repository: DatabaseRepository) {
    fun execute(vacancy: VacancyDomain) {
        repository.insertVacancy(vacancy)
    }
}