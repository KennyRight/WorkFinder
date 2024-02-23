package com.example.workfinder.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workfinder.domain.interactors.GetVacanciesUseCase
import com.example.workfinder.domain.interactors.SearchVacanciesUseCase
import com.example.workfinder.domain.models.VacancyDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val searchVacanciesUseCase: SearchVacanciesUseCase
): ViewModel() {
    private val _vacancyList = MutableLiveData<List<VacancyDomain>>()
    val vacancyList : LiveData<List<VacancyDomain>> get() = _vacancyList

    fun fetchVacancies() {
        viewModelScope.launch {
            try {
                val res = getVacanciesUseCase.execute()
                withContext(Dispatchers.Main) {
                    _vacancyList.value = res
                }
            }catch (e: Exception) {
                Log.e("log", e.message.toString())
                _vacancyList.value = emptyList()
            }
        }
    }


    fun searchVacancies(query: String) {
        viewModelScope.launch {
            try {
                val res = searchVacanciesUseCase.execute(query)
                withContext(Dispatchers.Main) {
                    _vacancyList.value = res
                }

            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }
        }
    }
}