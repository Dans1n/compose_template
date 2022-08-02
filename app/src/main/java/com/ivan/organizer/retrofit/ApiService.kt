package com.ivan.organizer.retrofit

import com.ivan.organizer.retrofit.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("natural")
    suspend fun getData(): Response<ApiResponse>
}