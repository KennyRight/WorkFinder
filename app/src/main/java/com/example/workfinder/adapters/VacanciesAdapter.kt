package com.example.workfinder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workfinder.VacancyDetailActivity
import com.example.workfinder.api.Vacancy
import com.example.workfinder.api.VacancyDetails
import com.example.workfinder.databinding.JobItemBinding

class VacanciesAdapter(private val vacancies: List<Vacancy>,private val context: Context) : RecyclerView.Adapter<VacanciesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)

        val bundle = Bundle()
        bundle.putString("jobName", vacancy.vacancy.jobName)
        bundle.putString("source", vacancy.vacancy.company.name)
        bundle.putString("duty", vacancy.vacancy.duty)
        bundle.putString("salary", vacancy.vacancy.salary)
        bundle.putString("contact_person", vacancy.vacancy.contact_person)
        bundle.putString("email", vacancy.vacancy.company.email)
        bundle.putString("phone", vacancy.vacancy.company.phone)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, VacancyDetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    inner class ViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(vacancy: Vacancy) {
            binding.vacancyName.text = vacancy.vacancy.jobName
            binding.salary.text = vacancy.vacancy.salary
            binding.region.text = vacancy.vacancy.region.name
            binding.employment.text = vacancy.vacancy.employment
        }
    }
}