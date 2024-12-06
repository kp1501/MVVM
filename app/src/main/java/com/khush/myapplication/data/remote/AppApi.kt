package com.khush.myapplication.data.remote

import com.khush.myapplication.data.remote.response.WeatherResponse
import com.khush.myapplication.util.Constants.API_GET_WEATHER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {

    @GET(API_GET_WEATHER)
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Response<WeatherResponse>

}