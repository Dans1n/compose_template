package com.ivan.organizer.di

import com.ivan.organizer.retrofit.TasksApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideCountriesApi(retrofit: Retrofit): TasksApi {
        return retrofit.create(TasksApi::class.java)
    }
    factory { provideCountriesApi(get()) }
}