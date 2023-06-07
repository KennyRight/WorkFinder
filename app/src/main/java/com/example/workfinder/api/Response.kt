package com.example.workfinder.api

import com.google.gson.annotations.SerializedName

data class VacanciesResponse(
    val status: String,
    val request: Request,
    val meta: Meta,
    val results: Results
)

data class Request(
    val api: String
)

data class Meta(
    val total: Int,
    val limit: Int
)

data class Results(
    val vacancies: List<Vacancy>
)

data class Vacancy(
    val vacancy: VacancyDetails
)

data class VacancyDetails(
    val id: String,
    val source: String,
    val region: Region,
    val company: Company,
    val creationDate: String,
    val salary: String,
    val salary_min: Int,
    val salary_max: Int,
    @SerializedName("job-name")
    val jobName: String,
    val vacancyUrl: String,
    val employment: String,
    val schedule: String,
    val duty: String,
    val category: Category,
    val requirement: Requirement,
    val addresses: Addresses,
    val social_protected: String,
    val term: Term,
    val contact_list: List<Contact>,
    val contact_person: String,
    val work_places: Int,
    val code_profession: String,
    val currency: String
)

data class Region(
    val region_code: String,
    val name: String
)

data class Company(
    val companycode: String,
    val email: String,
    val hr_agency: Boolean,
    val inn: String,
    val kpp: String,
    val name: String,
    val ogrn: String,
    val phone: String,
    val url: String
)

data class Category(
    val specialisation: String
)

data class Requirement(
    val education: String,
    val experience: Int
)

data class Addresses(
    val address: List<Address>
)

data class Address(
    val location: String,
    val lng: String,
    val lat: String
)

data class Term(
    val text: String
)

data class Contact(
    val contact_type: String,
    val contact_value: String
)
