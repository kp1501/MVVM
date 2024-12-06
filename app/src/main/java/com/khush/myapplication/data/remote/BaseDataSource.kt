package com.khush.myapplication.data.remote

import android.util.Log
import com.khush.myapplication.data.remote.response.BaseResponse
import com.khush.myapplication.util.ApiResources
import retrofit2.Response

open class BaseDataSource {
    open suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResources<T> {
        try {

            val response = call()
            if (response.code() == 403) {
                Log.d("TAG", "response 403")
                return ApiResources.Error("", code = response.code())
            } else if (response.body() != null) {

                response.body()?.let {
                    return ApiResources.Success(it)
                }

                /*val baseResponse = (response.body() as BaseResponse)
                Log.d("TAG", "response ${baseResponse.code}")

                if (baseResponse.code == 200) {
                    Log.d("TAG", "response 200")

                } else {
                    val body = response.body()
                    if (body != null) {
                        var msg = baseResponse.message.trim()
                        if (msg.isEmpty()) {
                            msg = "Internal server error"
                        }
                        return ApiResources.Error(msg)
                    }
                }*/
                return error("Success => ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return error("Error => ${e.message} ?: $e")
        }
        return ApiResources.Error("")
    }

    private fun <T> error(message: String): ApiResources<T> {
        return ApiResources.Error(message)
    }
}