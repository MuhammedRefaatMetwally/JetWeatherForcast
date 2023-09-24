package com.example.jetweatherforcast.network

import com.example.jetweatherforcast.model.WeatherResponse
import com.example.jetweatherforcast.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units : String = "imperial",
        @Query("appid") appid : String = Constants.API_KEY
    ) : WeatherResponse
}