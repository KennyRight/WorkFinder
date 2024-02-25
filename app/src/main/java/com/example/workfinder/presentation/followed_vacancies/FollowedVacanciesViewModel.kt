package com.example.workfinder.presentation.followed_vacancies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workfinder.domain.interactors.DeleteSavedVacancyUseCase
import com.example.workfinder.domain.interactors.GetSavedVacanciesUseCase
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
class FollowedVacanciesViewModel @Inject constructor(
    private val getSavedVacanciesUseCase: GetSavedVacanciesUseCase,
    private val deleteSavedVacancyUseCase: DeleteSavedVacancyUseCase
): ViewModel() {
    private val _vacancyList = MutableLiveData<List<VacancyDomain>>()
    val vacancyList : LiveData<List<VacancyDomain>> get() = _vacancyList

    fun fetchVacancies() {
        viewModelScope.launch {
            try {
                var res: List<VacancyDomain> = emptyList()
                withContext(Dispatchers.IO) {
                    res = getSavedVacanciesUseCase.execute()
                }
                withContext(Dispatchers.Main) {
                    _vacancyList.value = res
                }
            }catch (e: Exception) {
                Log.e("log", e.message.toString())
                _vacancyList.value = emptyList()
            }
        }
    }


    fun deleteVacancy(id: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteSavedVacancyUseCase.execute(id)
                }
            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }
        }
        fetchVacancies()
    }
}