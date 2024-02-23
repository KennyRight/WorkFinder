package com.example.workfinder.data

import com.example.workfinder.data.api.VacanciesResponse
import com.example.workfinder.data.api.Vacancy
import com.example.workfinder.domain.models.VacancyDomain

fun vacancyFromAPIMapper(vacanciesResponse: VacanciesResponse): List<VacancyDomain> {
    return vacanciesResponse.results.vacancies.map { vacancy: Vacancy ->
        VacancyDomain(
            id = 0,
            contact_person = vacancy.vacancy.contact_person,
            duty = vacancy.vacancy.duty,
            email = vacancy.vacancy.company?.email,
            employment = vacancy.vacancy.employment,
            jobName = vacancy.vacancy.jobName,
            phone = vacancy.vacancy.company?.phone,
            region = vacancy.vacancy.region?.name,
            salary = vacancy.vacancy.salary,
            source = vacancy.vacancy.source
        )
    }
}

fun vacanciesFromDatabaseMapper(vacancy: List<com.example.workfinder.data.database.Vacancy>): List<VacancyDomain> {
    return vacancy.map { vacancyFromDatabaseMapper(it) }
}

fun vacanciesToDatabaseMapper(vacancy: List<VacancyDomain>): List<com.example.workfinder.data.database.Vacancy> {
    return vacancy.map { vacancyToDatabaseMapper(it) }
}

fun vacancyFromDatabaseMapper(vacancy: com.example.workfinder.data.database.Vacancy): VacancyDomain {
    return VacancyDomain(
        jobName = vacancy.jobName,
        contact_person = vacancy.contact_person,
        duty = vacancy.duty,
        email = vacancy.email,
        employment = vacancy.employment,
        phone = vacancy.phone,
        region = vacancy.region,
        salary = vacancy.salary,
        source = vacancy.source,
        id = 0
    )
}

fun vacancyToDatabaseMapper(vacancy: VacancyDomain): com.example.workfinder.data.database.Vacancy {
    return com.example.workfinder.data.database.Vacancy(
        jobName = vacancy.jobName,
        contact_person = vacancy.contact_person,
        duty = vacancy.duty,
        email = vacancy.email,
        employment = vacancy.employment,
        phone = vacancy.phone,
        region = vacancy.region,
        salary = vacancy.salary,
        source = vacancy.source,
    )
}