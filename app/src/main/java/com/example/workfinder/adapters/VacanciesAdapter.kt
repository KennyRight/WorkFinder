package com.example.workfinder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.workfinder.FollowedVacanciesActivity
import com.example.workfinder.MainActivity
import com.example.workfinder.VacancyDetailActivity
import com.example.workfinder.api.Vacancy
import com.example.workfinder.api.VacancyDetails
import com.example.workfinder.database.VacanciesDao
import com.example.workfinder.database.VacanciesDatabase
import com.example.workfinder.databinding.JobItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VacanciesAdapter(private val vacancies: List<Vacancy>, private val context: Context) :
    RecyclerView.Adapter<VacanciesAdapter.ViewHolder>() {
    private lateinit var vacanciesDao: VacanciesDao
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)

        val bundle = Bundle()
        bundle.putString("jobName", vacancy.vacancy.jobName)
        bundle.putString("source", vacancy.vacancy.company?.name)
        bundle.putString("duty", vacancy.vacancy.duty)
        bundle.putString("salary", vacancy.vacancy.salary)
        bundle.putString("contact_person", vacancy.vacancy.contact_person)
        bundle.putString("email", vacancy.vacancy.company?.email)
        bundle.putString("phone", vacancy.vacancy.company?.phone)
        bundle.putString("region", vacancy.vacancy.region?.name)
        bundle.putString("employment", vacancy.vacancy.employment)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, VacancyDetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    inner class ViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(vacancy: Vacancy) {
            binding.vacancyName.text = vacancy.vacancy.jobName
            binding.salary.text = vacancy.vacancy.salary + " руб."
            binding.region.text = vacancy.vacancy.region!!.name
            binding.employment.text = vacancy.vacancy.employment
            if (context is FollowedVacanciesActivity) {
                binding.deleteFromFollowedButton.visibility = View.VISIBLE
            }
            binding.deleteFromFollowedButton.setOnClickListener {
                GlobalScope.launch {
                    vacanciesDao = VacanciesDatabase
                        .getDatabase(context)
                        .vacanciesDao()
                    vacancy.vacancy.id?.toInt()?.let { it1 -> vacanciesDao.deleteVacancyById(it1) }

                }
                if (context is FollowedVacanciesActivity) {
                    context.recreate()
                }
            }
        }
    }
}