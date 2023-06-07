package com.example.workfinder

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workfinder.adapters.VacanciesAdapter
import com.example.workfinder.databinding.ActivityMainBinding
import com.example.workfinder.databinding.ActivityVacancyDetailBinding

class VacancyDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityVacancyDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVacancyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val receivedBundle = intent.extras

        val input = receivedBundle?.getString("duty")
        val output = input
            ?.replace("<p>".toRegex(), "")
            ?.replace("</p>".toRegex(), "")
            ?.replace("<ul>".toRegex(), "")
            ?.replace("</ul>".toRegex(), "")
            ?.replace("<li>".toRegex(), "")
            ?.replace("</li>".toRegex(), "")

        binding.duty.text = output
        binding.salary.text = receivedBundle?.getString("salary") + " руб."
        binding.source.text = receivedBundle?.getString("source")
        binding.vacancyName.text = receivedBundle?.getString("jobName")
        binding.contactPerson.text = receivedBundle?.getString("contact_person")
        binding.email.text = receivedBundle?.getString("email")
        binding.phone.text = receivedBundle?.getString("phone")
    }
}