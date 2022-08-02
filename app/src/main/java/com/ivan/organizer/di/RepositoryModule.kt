package com.ivan.organizer.di

import android.content.Context
import com.ivan.organizer.database.dao.TasksDao
import com.ivan.organizer.retrofit.TasksApi
import com.ivan.organizer.retrofit.Repository
import com.ivan.organizer.retrofit.RepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideRepository(api: TasksApi, context: Context, dao : TasksDao): Repository {
        return RepositoryImpl(api, context, dao)
    }
    single { provideRepository(get(), androidContext(), get()) }
}