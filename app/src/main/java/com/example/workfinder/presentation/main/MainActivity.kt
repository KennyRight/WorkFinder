package com.example.workfinder.presentation.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workfinder.R
import com.example.workfinder.presentation.adapters.VacanciesAdapter
import com.example.workfinder.databinding.ActivityMainBinding
import com.example.workfinder.domain.models.VacancyDomain
import com.example.workfinder.presentation.followed_vacancies.FollowedVacanciesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var orientationEventListener: OrientationEventListener
    private lateinit var vacanciesAdapter: VacanciesAdapter
    private val viewModel: MainViewModel by viewModels()
    companion object {
        lateinit var binding: ActivityMainBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.vacancyList.observe(this) { list ->
            setVacancies(list)
        }

        viewModel.fetchVacancies()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isEmpty()) {
                    viewModel.fetchVacancies()
                } else {
                    viewModel.searchVacancies(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText.isEmpty()) {
                    viewModel.fetchVacancies()
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

    private fun setVacancies(response: List<VacancyDomain>) {
        vacanciesAdapter = VacanciesAdapter(response, this@MainActivity)
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