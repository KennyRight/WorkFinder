package com.example.workfinder.presentation.vacancy_detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.data.database.VacanciesDatabase
import com.example.workfinder.data.database.Vacancy
import com.example.workfinder.databinding.ActivityVacancyDetailBinding
import com.example.workfinder.domain.models.VacancyDomain
import com.example.workfinder.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class VacancyDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVacancyDetailBinding
    private lateinit var vacanciesDao: VacanciesDao
    private val viewModel: VacancyDetailViewModel by viewModels()
    private lateinit var vacancy: VacancyDomain

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
            ?.replace("<br>".toRegex(), "")
            ?.replace("</br>".toRegex(), "")
            ?.replace("<b>".toRegex(), "")
            ?.replace("</b>".toRegex(), "")

        val salary = receivedBundle?.getString("salary")
        val source = receivedBundle?.getString("source")
        val vacancyName = receivedBundle?.getString("jobName")
        val contactPerson = receivedBundle?.getString("contact_person")
        val email = receivedBundle?.getString("email")
        val phone = receivedBundle?.getString("phone")
        val region = receivedBundle?.getString("region")
        val employment = receivedBundle?.getString("employment")
        val visibility = receivedBundle?.getString("visibility")

        vacancy = VacancyDomain(
            id = 0,
            jobName = vacancyName,
            duty = duty,
            salary = salary,
            contact_person = contactPerson,
            email = email,
            phone = phone,
            source = source,
            region = region,
            employment = employment
        )

        binding.duty.text = duty
        binding.salary.text = salary + " руб."
        binding.source.text = source
        binding.vacancyName.text = vacancyName
        binding.contactPerson.text = contactPerson
        binding.email.text = email
        binding.phone.text = phone
        if (visibility == "visible") {
            binding.addToFollowedButton.visibility = View.VISIBLE
        }

        binding.addToFollowedButton.setOnClickListener {
            viewModel.saveVacancy(vacancy)
            Toast.makeText(this@VacancyDetailActivity
                , "Вакансия добавлена в отслеживаемые", Toast.LENGTH_SHORT).show()
        }
    }
}