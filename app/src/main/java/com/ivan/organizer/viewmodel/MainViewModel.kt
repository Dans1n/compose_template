package com.ivan.organizer.viewmodel

import androidx.lifecycle.*
import com.ivan.organizer.data.SampleDataList
import com.ivan.organizer.database.TasksDatabase
import com.ivan.organizer.database.model.TasksData
import com.ivan.organizer.retrofit.*
import com.ivan.organizer.retrofit.response.ApiResponse
import com.ivan.organizer.retrofit.response.SampleData
import com.ivan.organizer.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val showLoading = MutableStateFlow(false)
    val countriesList = MutableStateFlow<List<TasksData>>(emptyList())
    val showError = SingleLiveEvent<String?>()

    val dataList = MutableStateFlow(listOf<SampleData>())
    val response = MutableStateFlow(ApiResponse())
    val error = MutableStateFlow("")
    val exception = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataList.emit(SampleDataList.list)
        }
    }

    fun updateList(value: SampleData){
        dataList.value = dataList.value.toMutableList().plus(value)
    }

    suspend fun deleteAll(){
        repository.deleteAll()
    }

    suspend fun getAllCountries() {
        showLoading.emit(true)
        viewModelScope.launch {
            val result =  repository.getAllCountries()
            showLoading.emit(false)
            when (result) {
                is AppResult.Success -> {
                    countriesList.emit(result.successData)
                    showError.value = null
                }
                is AppResult.Error -> {
                    showError.value = result.exception.message
                }
            }
        }
    }

    /*private suspend fun getList(){
        when(val handle = handleApi { repository.api.getData() }){
            is ApiSuccess -> response.emit(handle.data)
            is ApiError -> error.emit("${handle.code} ${handle.message}")
            is ApiException -> exception.emit("${handle.e.message}")
        }
    }*/

    private suspend fun <T: Any> handleApi(execute: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null){
                ApiSuccess(body)
            } else {
                ApiError(response.code(), response.message())
            }
        } catch (e: HttpException){
            ApiError(e.code(), e.message())
        } catch (e: Throwable) {
            ApiException(e)
        }
    }
}
