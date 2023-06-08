package com.example.workfinder

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.workfinder.adapters.VacanciesAdapter
import com.example.workfinder.database.VacanciesDao
import com.example.workfinder.database.VacanciesDatabase
import com.example.workfinder.database.Vacancy
import com.example.workfinder.databinding.ActivityMainBinding
import com.example.workfinder.databinding.ActivityVacancyDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VacancyDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityVacancyDetailBinding
    private lateinit var vacanciesDao: VacanciesDao
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVacancyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val receivedBundle = intent.extras

        val input = receivedBundle?.getString("duty")
        val duty = input
            ?.replace("<p>".toRegex(), "")
            ?.replace("</p>".toRegex(), "")
            ?.replace("<ul>".toRegex(), "")
            ?.replace("</ul>".toRegex(), "")
            ?.replace("<li>".toRegex(), "")
            ?.replace("</li>".toRegex(), "")

        val salary = receivedBundle?.getString("salary")
        val source = receivedBundle?.getString("source")
        val vacancyName = receivedBundle?.getString("jobName")
        val contactPerson = receivedBundle?.getString("contact_person")
        val email = receivedBundle?.getString("email")
        val phone = receivedBundle?.getString("phone")
        val region = receivedBundle?.getString("region")
        val employment = receivedBundle?.getString("employment")

        binding.duty.text = duty
        binding.salary.text = salary + " руб."
        binding.source.text = source
        binding.vacancyName.text = vacancyName
        binding.contactPerson.text = contactPerson
        binding.email.text = email
        binding.phone.text = phone
        binding.addToFollowedButton.visibility = View.VISIBLE

        binding.addToFollowedButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    vacanciesDao = VacanciesDatabase
                        .getDatabase(this@VacancyDetailActivity)
                        .vacanciesDao()
                    vacanciesDao.insertVacancy(Vacancy(jobName = vacancyName, salary = salary,
                        contact_person = contactPerson, duty = duty, email = email,
                        phone = phone, source = source, employment = region, region = employment))
                }
            }
            Toast.makeText(this@VacancyDetailActivity
                , "Вакансия добавлена в отслеживаемые", Toast.LENGTH_SHORT).show()
        }
    }
}