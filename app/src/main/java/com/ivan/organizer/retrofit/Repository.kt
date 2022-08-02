package com.ivan.organizer.retrofit

import android.content.Context
import android.util.Log
import com.ivan.organizer.database.model.TasksData
import com.ivan.organizer.database.dao.TasksDao
import com.ivan.organizer.util.NetworkManager.isOnline
import com.ivan.organizer.util.TAG
import com.ivan.organizer.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface Repository {
    suspend fun getAllCountries() : AppResult<List<TasksData>>
    suspend fun deleteAll()
}

class RepositoryImpl(private val api: TasksApi,
                     private val context: Context,
                     private val dao: TasksDao) :
    Repository {

    override suspend fun getAllCountries(): AppResult<List<TasksData>> {
        if (isOnline(context)) {
            return try {
                val response = api.getAllCountries()
                if (response.isSuccessful) {
                    //save the data
                    response.body()?.let {
                        withContext(Dispatchers.IO) { dao.add(it) }
                    }
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            //check in db if the data exists
            val data = getCountriesDataFromCache()
            return if (data.isNotEmpty()) {
                Log.d(TAG, "from db")
                AppResult.Success(data)
            } else
            //no network
                context.noNetworkConnectivityError()
        }
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private suspend fun getCountriesDataFromCache(): List<TasksData> {
        return withContext(Dispatchers.IO) {
            dao.findAll()
        }
    }
}