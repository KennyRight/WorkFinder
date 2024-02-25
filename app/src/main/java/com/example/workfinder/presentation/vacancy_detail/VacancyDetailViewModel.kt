package com.example.workfinder.presentation.vacancy_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workfinder.domain.interactors.GetVacanciesUseCase
import com.example.workfinder.domain.interactors.SaveVacancyUseCase
import com.example.workfinder.domain.interactors.SearchVacanciesUseCase
import com.example.workfinder.domain.models.VacancyDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VacancyDetailViewModel @Inject constructor(
    private val saveVacancyUseCase: SaveVacancyUseCase,
): ViewModel() {
    fun saveVacancy(vacancy: VacancyDomain) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    saveVacancyUseCase.execute(vacancy)
                }
            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }
        }
    }
}