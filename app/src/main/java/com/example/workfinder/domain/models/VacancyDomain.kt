package com.example.workfinder.domain.models


data class VacancyDomain(
    val id: Int,
    val jobName: String?,
    val duty: String?,
    val salary: String?,
    val contact_person: String?,
    val email: String?,
    val phone: String?,
    val source: String?,
    val region: String?,
    val employment: String?
)