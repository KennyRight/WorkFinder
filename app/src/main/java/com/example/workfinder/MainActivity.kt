package com.example.workfinder

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workfinder.adapters.VacanciesAdapter
import com.example.workfinder.data.api.Instance
import com.example.workfinder.data.api.VacanciesResponse
import com.example.workfinder.databinding.ActivityMainBinding
import com.example.workfinder.domain.models.VacancyDomain
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var orientationEventListener: OrientationEventListener
    private lateinit var vacanciesAdapter: VacanciesAdapter
    companion object {
        lateinit var binding: ActivityMainBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = Instance.api
        val call = service.getVacancies()
//        fetchVacancies(call)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val call = if (query.isEmpty()) {
                    service.getVacancies()
                } else {
                    service.searchVacancies(query)
                }
//                fetchVacancies(call)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    val call = service.getVacancies()
//                    fetchVacancies(call)
                }
                return true
            }
        })

        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                // Обрабатываем изменение ориентации экрана
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = windowManager.defaultDisplay.rotation
                val newOrientation = when (rotation) {
                    Surface.ROTATION_0 -> Configuration.ORIENTATION_PORTRAIT
                    Surface.ROTATION_90 -> Configuration.ORIENTATION_LANDSCAPE
                    Surface.ROTATION_180 -> Configuration.ORIENTATION_PORTRAIT
                    Surface.ROTATION_270 -> Configuration.ORIENTATION_LANDSCAPE
                    else -> Configuration.ORIENTATION_UNDEFINED
                }

                // Выполняем действия при изменении ориентации экрана
                handleOrientationChange(newOrientation)
            }
        }

        // Запускаем слушатель событий
        orientationEventListener.enable()
    }

    private fun fetchVacancies(response: List<VacancyDomain>) {
        val vacancies = response
        vacanciesAdapter = VacanciesAdapter(vacancies, this@MainActivity)
        binding.vacanciesList.adapter = vacanciesAdapter
        binding.vacanciesList.layoutManager = LinearLayoutManager(this@MainActivity)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.followed_vacancies -> {
                val intent = Intent(this, FollowedVacanciesActivity::class.java)
                this.startActivity(intent)
            }
        }
        return true
    }

    private fun handleOrientationChange(orientation: Int) {
        // Пример: Блокируем ориентацию на портретную или ландшафтную, в зависимости от текущей ориентации
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rightSideBar.visibility = View.GONE
        }
    }
}