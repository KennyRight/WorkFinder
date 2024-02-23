package com.example.workfinder.di

import com.example.workfinder.domain.ApiRepository
import com.example.workfinder.domain.DatabaseRepository
import com.example.workfinder.domain.interactors.DeleteSavedVacancyUseCase
import com.example.workfinder.domain.interactors.GetSavedVacanciesUseCase
import com.example.workfinder.domain.interactors.GetVacanciesUseCase
import com.example.workfinder.domain.interactors.SaveVacancyUseCase
import com.example.workfinder.domain.interactors.SearchVacanciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGetVacanciesUseCase(repository: ApiRepository): GetVacanciesUseCase {
        return GetVacanciesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSearchVacanciesUseCase(repository: ApiRepository): SearchVacanciesUseCase {
        return SearchVacanciesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveVacancyUseCase(repository: DatabaseRepository): SaveVacancyUseCase {
        return SaveVacancyUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSavedVacancyUseCase(repository: DatabaseRepository): GetSavedVacanciesUseCase {
        return GetSavedVacanciesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedVacancyUseCase(repository: DatabaseRepository): DeleteSavedVacancyUseCase {
        return DeleteSavedVacancyUseCase(repository)
    }
}