package com.example.workfinder.presentation.followed_vacancies

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workfinder.presentation.adapters.VacanciesAdapter
import com.example.workfinder.data.api.Addresses
import com.example.workfinder.data.api.Category
import com.example.workfinder.data.api.Company
import com.example.workfinder.data.api.Region
import com.example.workfinder.data.api.Requirement
import com.example.workfinder.data.api.Term
import com.example.workfinder.data.api.Vacancy
import com.example.workfinder.data.api.VacancyDetails
import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.data.database.VacanciesDatabase
import com.example.workfinder.databinding.ActivityFollowedVacanciesBinding
import com.example.workfinder.domain.models.VacancyDomain
import com.example.workfinder.presentation.adapters.FollowedVacanciesAdapter
import com.example.workfinder.presentation.main.MainActivity
import com.example.workfinder.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FollowedVacanciesActivity : AppCompatActivity() {
    private lateinit var orientationEventListener: OrientationEventListener
    private lateinit var vacanciesAdapter: FollowedVacanciesAdapter
    private val viewModel: FollowedVacanciesViewModel by viewModels()
    private lateinit var binding: ActivityFollowedVacanciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowedVacanciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.vacancyList.observe(this) { list ->
            setVacancies(list)
        }

        viewModel.fetchVacancies()

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

    private fun handleOrientationChange(orientation: Int) {
        // Пример: Блокируем ориентацию на портретную или ландшафтную, в зависимости от текущей ориентации
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rightSideBar.visibility = View.GONE
        }
    }

    private fun setVacancies(response: List<VacancyDomain>) {
        vacanciesAdapter = FollowedVacanciesAdapter(response, this, binding, viewModel)
        binding.vacanciesList.adapter = vacanciesAdapter
        binding.vacanciesList.layoutManager = LinearLayoutManager(this)
    }

}