package com.ivan.organizer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivan.organizer.database.model.TasksData

@Dao
interface TasksDao {

    @Query("SELECT * FROM Tasks")
    fun findAll(): List<TasksData>

    @Query("DELETE FROM Tasks")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<TasksData>)
}