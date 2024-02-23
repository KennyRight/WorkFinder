package com.example.workfinder.domain

import com.example.workfinder.data.database.Vacancy
import com.example.workfinder.domain.models.VacancyDomain

interface DatabaseRepository {

    fun getAllVacancies(): List<VacancyDomain>

    fun insertVacancy(vacancy: VacancyDomain)

    fun deleteVacancyById(vacancyId: Int)

}