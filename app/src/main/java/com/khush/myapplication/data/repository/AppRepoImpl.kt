package com.khush.myapplication.data.repository

import com.khush.myapplication.data.local.AppDatabase
import com.khush.myapplication.data.local.model.UserEntity
import com.khush.myapplication.data.remote.AppApi
import com.khush.myapplication.data.remote.request.WeatherRequest
import com.khush.myapplication.data.remote.response.WeatherResponse
import com.khush.myapplication.domain.AppRepository
import retrofit2.Response
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val appApi: AppApi
) : AppRepository {

    override suspend fun insert(userEntity: UserEntity) {
        appDatabase.appDao().insert(userEntity)
    }

    override fun getAll(): List<UserEntity> {
        return appDatabase.appDao().getAll()
    }

    override suspend fun getWeather(weatherRequest: WeatherRequest): Response<WeatherResponse> {
        return appApi.getWeather(
            latitude = weatherRequest.latitude,
            longitude = weatherRequest.longitude,
            units = weatherRequest.units,
            appId = weatherRequest.appId
        )
    }
}