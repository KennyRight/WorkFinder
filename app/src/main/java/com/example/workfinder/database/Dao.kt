package com.example.workfinder.database

import androidx.room.*


@Dao
interface VacanciesDao {
    // Получить все фильмы из базы данных
    @Query("SELECT * FROM vacancy")
    fun getAllVacancies(): List<Vacancy>

    // Вставить фильм в базу данных
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVacancy(vacancy: Vacancy)

    // Удалить все фильмы из базы данных
    @Query("DELETE FROM Vacancy")
    fun deleteVacancies()

    // Удалить конкретный фильм из БД
    @Delete
    fun deleteVacancy(vacancy: Vacancy)
}