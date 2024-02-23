package com.example.workfinder.data

import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.domain.DatabaseRepository
import com.example.workfinder.domain.models.VacancyDomain

class DatabaseRepositoryImpl(private val vacanciesDao: VacanciesDao): DatabaseRepository {
    override fun getAllVacancies(): List<VacancyDomain> {
        return vacanciesFromDatabaseMapper(vacanciesDao.getAllVacancies())
    }

    override fun insertVacancy(vacancy: VacancyDomain) {
        vacanciesDao.insertVacancy(vacancyToDatabaseMapper(vacancy))
    }

    override fun deleteVacancyById(vacancyId: Int) {
        vacanciesDao.deleteVacancyById(vacancyId)
    }
}