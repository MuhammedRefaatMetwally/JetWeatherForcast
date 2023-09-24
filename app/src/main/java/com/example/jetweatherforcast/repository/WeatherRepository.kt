package com.example.jetweatherforcast.repository

import android.util.Log
import com.example.jetweatherforcast.data.DataOrException
import com.example.jetweatherforcast.model.WeatherResponse
import com.example.jetweatherforcast.network.WeatherApi
import retrofit2.Call
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityName: String, units: String): DataOrException<WeatherResponse, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityName, units = units)
        } catch (e: java.lang.Exception) {
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }
}