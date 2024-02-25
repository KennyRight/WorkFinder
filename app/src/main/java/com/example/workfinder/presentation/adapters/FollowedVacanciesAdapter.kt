package com.example.workfinder.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.workfinder.presentation.followed_vacancies.FollowedVacanciesActivity
import com.example.workfinder.presentation.main.MainActivity
import com.example.workfinder.presentation.vacancy_detail.VacancyDetailActivity
import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.data.database.VacanciesDatabase
import com.example.workfinder.databinding.ActivityFollowedVacanciesBinding
import com.example.workfinder.databinding.JobItemBinding
import com.example.workfinder.domain.models.VacancyDomain
import com.example.workfinder.presentation.followed_vacancies.FollowedVacanciesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FollowedVacanciesAdapter(
    private val vacancies: List<VacancyDomain>,
    private val context: Context,
    private val binding: ActivityFollowedVacanciesBinding,
    private val viewModel: FollowedVacanciesViewModel
) :
    RecyclerView.Adapter<FollowedVacanciesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)

        val bundle = Bundle()
        bundle.putString("jobName", vacancy.jobName)
        bundle.putString("source", vacancy.source)
        bundle.putString("duty", vacancy.duty)
        bundle.putString("salary", vacancy.salary)
        bundle.putString("contact_person", vacancy.contact_person)
        bundle.putString("email", vacancy.email)
        bundle.putString("phone", vacancy.phone)
        bundle.putString("region", vacancy.region)
        bundle.putString("employment", vacancy.employment)
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

                    binding.rightSideBar.visibility = View.VISIBLE
                    binding.vacancyName.text = vacancy.jobName
                    binding.source.text = vacancy.source
                    binding.salary.text = vacancy.salary + " руб."
                    binding.duty.text = vacancy.duty
                    binding.email.text = vacancy.email
                    binding.phone.text = vacancy.phone
                    binding.contactPerson.text = vacancy.contact_person

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
        fun bind(vacancy: VacancyDomain) {
            binding.vacancyName.text = vacancy.jobName
            binding.salary.text = vacancy.salary + " руб."
            binding.region.text = vacancy.region
            binding.employment.text = vacancy.employment
            if (context is FollowedVacanciesActivity) {
                binding.deleteFromFollowedButton.visibility = View.VISIBLE
            }
            binding.deleteFromFollowedButton.setOnClickListener {
                viewModel.deleteVacancy(vacancy.id)
            }
        }
    }
}