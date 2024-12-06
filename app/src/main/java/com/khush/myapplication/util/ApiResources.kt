package com.khush.myapplication.util

sealed class ApiResources<T>(val data: T? = null, val message: String? = null,code: Int?=null) {
    class Success<T>(data: T?,message: String?=""): ApiResources<T>(data,message)
    class Error<T>(message: String, data: T? = null,code: Int=200): ApiResources<T>(data, message,code)
    class Loading<T>(val isLoading: Boolean = true): ApiResources<T>(null)
    class Unknown<T>(): ApiResources<T>(null)
}
