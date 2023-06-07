package com.example.workfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workfinder.adapters.VacanciesAdapter
import com.example.workfinder.api.Instance
import com.example.workfinder.api.VacanciesResponse
import com.example.workfinder.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var vacanciesAdapter: VacanciesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = Instance.api
        val call = service.getVacancies()
        fetchVacancies(call)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val call = if (query.isEmpty()) {
                    service.getVacancies()
                } else {
                    service.searchVacancies(query)
                }
                fetchVacancies(call)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    val call = service.getVacancies()
                    fetchVacancies(call)
                }
                return true
            }
        })
    }

    private fun fetchVacancies(call: Call<VacanciesResponse>) {
        call.enqueue(object : Callback<VacanciesResponse> {
            override fun onResponse(call: Call<VacanciesResponse>, response: Response<VacanciesResponse>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    val vacancies = response?.results?.vacancies
                    vacanciesAdapter = vacancies?.let { VacanciesAdapter(it, this@MainActivity) }!!
                    binding.vacanciesList.adapter = vacanciesAdapter
                    binding.vacanciesList.layoutManager = LinearLayoutManager(this@MainActivity)
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VacanciesResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}