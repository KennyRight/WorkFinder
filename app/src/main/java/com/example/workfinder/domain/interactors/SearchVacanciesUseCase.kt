package com.example.workfinder.domain.interactors

import android.util.Log
import com.example.workfinder.domain.ApiRepository
import com.example.workfinder.domain.models.VacancyDomain

class SearchVacanciesUseCase(private val repository: ApiRepository) {
    suspend fun execute(query: String): List<VacancyDomain> {
        return try {
            repository.searchVacancies(query)
        } catch (e: Exception) {
            Log.e("nig", e.toString())
            emptyList()
            // Rethrow or return a default value depending on your use case
        }
    }
}