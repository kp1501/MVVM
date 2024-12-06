package com.khush.myapplication.presentation.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khush.myapplication.data.remote.BaseDataSource
import com.khush.myapplication.data.remote.request.WeatherRequest
import com.khush.myapplication.data.remote.response.WeatherResponse
import com.khush.myapplication.domain.AppRepository
import com.khush.myapplication.util.ApiResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val baseDataSource: BaseDataSource
) : ViewModel() {

    private val weatherState =
        MutableStateFlow<ApiResources<WeatherResponse>>(ApiResources.Unknown())
    val mWeatherState: StateFlow<ApiResources<WeatherResponse>> get() = weatherState

    /**
     * Function to call the weather state api and to get the response.
     * */
    fun getWeatherData(weatherRequest: WeatherRequest) {
        viewModelScope.launch {
            weatherState.apply {
                try {
                    Log.d("TAG", "getWeatherData: ")
//                    emit(ApiResources.Loading())
                    val data = appRepository.getWeather(weatherRequest)
                    Log.d("TAG", "getWeatherData: ${data.message()}")
                    emit(baseDataSource.getResult { data })
                } catch (e: HttpException) {
                    Log.d("TAG", "getWeatherData: ${e.message}")
                    emit(
                        ApiResources.Error(
                            e.localizedMessage ?: "An unexpected error occurred!"
                        )
                    )
                } catch (e: IOException) {
                    emit(ApiResources.Error("Couldn't reach server. Check your internet connection."))
                } catch (e: Exception) {
                    emit(ApiResources.Error(e.message ?: ""))
                }
            }
        }
    }
}