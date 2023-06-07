package com.example.workfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vacancy::class], version = 1)
abstract class VacanciesDatabase : RoomDatabase() {
    abstract fun vacanciesDao(): VacanciesDao

    companion object {
        @Volatile
        private var INSTANCE: VacanciesDatabase? = null

        //Получить экземпляр базы данных из другой области кода (например из главной активности)

        fun getDatabase(context: Context): VacanciesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VacanciesDatabase::class.java,
                    "vacancies_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}