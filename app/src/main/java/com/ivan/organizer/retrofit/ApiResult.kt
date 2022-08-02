package com.ivan.organizer.retrofit

import com.ivan.organizer.util.ApiErrorUtils
import retrofit2.Response

sealed interface ApiResult<T : Any>

class ApiSuccess<T : Any>(val data: T): ApiResult<T>
class ApiError<T: Any>(val code: Int, val message: String): ApiResult<T>
class ApiException<T: Any>(val e: Throwable): ApiResult<T>


sealed class AppResult<out T> {

    data class Success<out T>(val successData : T) : AppResult<T>()
    class Error(val exception: java.lang.Exception, val message: String = exception.localizedMessage)
        : AppResult<Nothing>()
}

fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
    val error = ApiErrorUtils.parseError(resp)
    return AppResult.Error(Exception(error.message))
}

fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
    response.body()?.let {
        return AppResult.Success(it)
    } ?: return handleApiError(response)
}