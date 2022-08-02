package com.ivan.organizer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivan.organizer.database.converter.*
import com.ivan.organizer.database.dao.TasksDao
import com.ivan.organizer.database.model.*


@Database(
    entities = [TasksData::class, Languages::class, Name::class, Native::class, Translations::class],
    version = 4, exportSchema = false
)
@TypeConverters(Converters::class, LanguagesTypeConverter::class, NameConverter::class, NativeConverter::class, TranslationsConverter::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract val tasksDao: TasksDao
}