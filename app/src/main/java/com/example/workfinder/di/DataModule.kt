package com.example.workfinder.di

import android.content.Context
import com.example.workfinder.data.ApiRepositoryImpl
import com.example.workfinder.data.DatabaseRepositoryImpl
import com.example.workfinder.data.api.ApiService
import com.example.workfinder.data.api.Instance
import com.example.workfinder.data.database.VacanciesDao
import com.example.workfinder.data.database.VacanciesDatabase
import com.example.workfinder.domain.ApiRepository
import com.example.workfinder.domain.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideApiRepository(apiService: ApiService): ApiRepository {
        return ApiRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(dao: VacanciesDao): DatabaseRepository {
        return DatabaseRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideDao(database: VacanciesDatabase): VacanciesDao {
        return database.vacanciesDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): VacanciesDatabase {
        return VacanciesDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Instance.api
    }
}