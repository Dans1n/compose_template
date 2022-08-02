package com.ivan.organizer.retrofit

import com.ivan.organizer.database.model.TasksData
import retrofit2.Response
import retrofit2.http.GET

interface TasksApi {
    @GET("/api/v1")
    suspend fun getAllCountries(): Response<List<TasksData>>
}