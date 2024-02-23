package com.example.workfinder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.workfinder.FollowedVacanciesActivity
import com.example.workfinder.MainActivity
import com.example.workfinder.VacancyDetailActivity
import com.example.workfinder.data.api.Vacancy
import com.example.workfinder.data.api.VacancyDetails
import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.data.database.VacanciesDatabase
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

    @SuppressLint("SetTextI18n")
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
        if (context is MainActivity) {
            bundle.putString("visibility", "visible")
        } else {
            bundle.putString("visibility", "gone")
        }


        holder.itemView.setOnClickListener {
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val rotation = display.rotation
            val config = context.resources.configuration

            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val intent = Intent(context, VacancyDetailActivity::class.java)
                    intent.putExtras(bundle)
                    context.startActivity(intent)
                }
            } else if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    if (context is MainActivity) {
                        MainActivity.binding.rightSideBar.visibility = View.VISIBLE
                        MainActivity.binding.vacancyName.text = vacancy.vacancy.jobName
                        MainActivity.binding.source.text = vacancy.vacancy.company?.name
                        MainActivity.binding.salary.text = vacancy.vacancy.salary + " руб."
                        MainActivity.binding.duty.text = vacancy.vacancy.duty
                        MainActivity.binding.email.text = vacancy.vacancy.company?.email
                        MainActivity.binding.phone.text = vacancy.vacancy.company?.phone
                        MainActivity.binding.contactPerson.text = vacancy.vacancy.contact_person
                        MainActivity.binding.addToFollowedButton.visibility = View.VISIBLE
                        MainActivity.binding.addToFollowedButton.setOnClickListener {
                            GlobalScope.launch {
                                vacanciesDao = VacanciesDatabase
                                    .getDatabase(context)
                                    .vacanciesDao()
                                vacanciesDao.insertVacancy(
                                    com.example.workfinder.data.database.Vacancy(
                                        jobName = vacancy.vacancy.jobName,
                                        salary = vacancy.vacancy.salary,
                                        contact_person = vacancy.vacancy.contact_person,
                                        duty = vacancy.vacancy.duty,
                                        email = vacancy.vacancy.company?.email,
                                        phone = vacancy.vacancy.company?.phone,
                                        source = vacancy.vacancy.company?.name,
                                        employment = vacancy.vacancy.employment,
                                        region = vacancy.vacancy.region?.name
                                    )
                                )

                            }
                            Toast.makeText(context
                                , "Вакансия добавлена в отслеживаемые", Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (context is FollowedVacanciesActivity) {
                        FollowedVacanciesActivity.binding.rightSideBar.visibility = View.VISIBLE
                        FollowedVacanciesActivity.binding.vacancyName.text = vacancy.vacancy.jobName
                        FollowedVacanciesActivity.binding.source.text = vacancy.vacancy.source
                        FollowedVacanciesActivity.binding.salary.text = vacancy.vacancy.salary + " руб."
                        FollowedVacanciesActivity.binding.duty.text = vacancy.vacancy.duty
                        FollowedVacanciesActivity.binding.email.text = vacancy.vacancy.company?.email
                        FollowedVacanciesActivity.binding.phone.text = vacancy.vacancy.company?.phone
                        FollowedVacanciesActivity.binding.contactPerson.text = vacancy.vacancy.contact_person
                    }
                }
            }

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