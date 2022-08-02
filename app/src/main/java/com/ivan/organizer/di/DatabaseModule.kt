package com.ivan.organizer.di

import android.app.Application
import androidx.room.Room
import com.ivan.organizer.database.TasksDatabase
import com.ivan.organizer.database.dao.TasksDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): TasksDatabase {
        return Room.databaseBuilder(application, TasksDatabase::class.java, "tasks")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: TasksDatabase): TasksDao {
        return database.tasksDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}