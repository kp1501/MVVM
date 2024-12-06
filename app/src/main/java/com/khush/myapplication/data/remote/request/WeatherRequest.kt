package com.khush.myapplication.data.remote.request

import com.google.gson.annotations.SerializedName

data class WeatherRequest(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("units")
    val units: String,
    @SerializedName("appid")
    val appId: String
)