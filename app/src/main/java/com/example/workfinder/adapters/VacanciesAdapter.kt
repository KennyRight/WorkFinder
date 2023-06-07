package com.example.workfinder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workfinder.api.Vacancy
import com.example.workfinder.databinding.JobItemBinding

class VacanciesAdapter(private val photos: List<Vacancy>,private val context: Context) : RecyclerView.Adapter<VacanciesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)

        holder.itemView.setOnClickListener {
            /*val intent = Intent(context, MovieInfoActivity::class.java)
            intent.putExtra("id", photo.id)
            context.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        return photos.size
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