package com.khush.myapplication.domain

import com.khush.myapplication.data.local.model.UserEntity
import com.khush.myapplication.data.remote.request.WeatherRequest
import com.khush.myapplication.data.remote.response.WeatherResponse
import retrofit2.Response

interface AppRepository {

    /**
     * Function for the Room database functions
     * */
    suspend fun insert(userEntity: UserEntity)

    fun getAll(): List<UserEntity>

    /**
     * Function to call the weather api and get the response
     * */
    suspend fun getWeather(weatherRequest: WeatherRequest): Response<WeatherResponse>

}