package com.example.workfinder.domain.interactors

import com.example.workfinder.domain.DatabaseRepository
import com.example.workfinder.domain.models.VacancyDomain

class DeleteSavedVacancyUseCase(private val repository: DatabaseRepository) {
    fun execute(id:Int) {
        repository.deleteVacancyById(id)
    }
}