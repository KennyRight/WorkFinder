package com.example.workfinder.data.database

import androidx.room.*


@Dao
interface VacanciesDao {

    @Query("SELECT * FROM vacancy")
    fun getAllVacancies(): List<Vacancy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVacancy(vacancy: Vacancy)

    @Query("DELETE FROM vacancy WHERE id = :vacancyId")
    fun deleteVacancyById(vacancyId: Int)
}