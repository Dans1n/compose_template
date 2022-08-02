package com.ivan.organizer.retrofit

import com.ivan.organizer.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder() {

    private val BASE_URL = "https://epic.gsfc.nasa.gov/api/"

    private val okhttp3client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            okhttp3client.addInterceptor(logging)
        }
    }

    private val contentType = "application/json".toMediaType()
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val serializationConverter = json.asConverterFactory(contentType)

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkhttpClient())
        .build()
        .create(ApiService::class.java)

    private fun provideOkhttpClient(): OkHttpClient {
        val client = okhttp3client
        val tokenInterceptor = Interceptor {
            val request = it.request().newBuilder()
                .build()
            return@Interceptor it.proceed(request)
        }
        client.apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            addInterceptor(tokenInterceptor)
        }
        return client.build()
    }
}