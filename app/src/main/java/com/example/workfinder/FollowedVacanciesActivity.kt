package com.example.workfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workfinder.adapters.VacanciesAdapter
import com.example.workfinder.api.*
import com.example.workfinder.database.VacanciesDao
import com.example.workfinder.database.VacanciesDatabase
import com.example.workfinder.database.Vacancy
import com.example.workfinder.databinding.ActivityFollowedVacanciesBinding
import com.example.workfinder.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowedVacanciesActivity : AppCompatActivity() {
    lateinit var binding: ActivityFollowedVacanciesBinding
    private lateinit var vacanciesDao: VacanciesDao
    private lateinit var vacanciesAdapter: VacanciesAdapter
    companion object {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowedVacanciesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                vacanciesDao = VacanciesDatabase
                    .getDatabase(this@FollowedVacanciesActivity)
                    .vacanciesDao()
                val vacanciesDB = vacanciesDao.getAllVacancies()
                val vacanciesList = emptyList<com.example.workfinder.api.Vacancy>()
                val vacancies = vacanciesList.toMutableList()
                vacanciesDB.forEach { vacancy ->
                    vacancies.add(
                        com.example.workfinder.api.Vacancy(
                            VacancyDetails(
                                id = vacancy.id.toString(),
                                source = "",
                                company = Company(
                                    companycode = "0",
                                    email = vacancy.email,
                                    hr_agency = false,
                                    inn = "",
                                    kpp = "",
                                    name = vacancy.source,
                                    ogrn = "",
                                    phone = vacancy.phone,
                                    url = ""
                                ),
                                region = Region(
                                    region_code = "",
                                    name = vacancy.region
                                ),
                                creationDate = "",
                                salary = vacancy.salary,
                                salary_max = 0,
                                salary_min = 0,
                                jobName = vacancy.jobName,
                                vacancyUrl = "",
                                employment = vacancy.employment,
                                schedule = "",
                                duty = vacancy.duty,
                                category = Category(""),
                                requirement = Requirement(education = "", experience = 0),
                                addresses = Addresses(emptyList()),
                                social_protected = "",
                                term = Term(""),
                                contact_list = emptyList(),
                                contact_person = vacancy.contact_person,
                                work_places = 0,
                                code_profession = "",
                                currency = ""
                            )
                        )
                    )
                }
                runOnUiThread {
                    vacanciesAdapter =
                        VacanciesAdapter(vacancies, this@FollowedVacanciesActivity)
                    binding.vacanciesList.adapter = vacanciesAdapter
                    binding.vacanciesList.layoutManager =
                        LinearLayoutManager(this@FollowedVacanciesActivity)
                }
            }

        }
    }

}