package com.example.jetweatherforcast.screens.main

import androidx.lifecycle.ViewModel
import com.example.jetweatherforcast.data.DataOrException
import com.example.jetweatherforcast.model.WeatherResponse
import com.example.jetweatherforcast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {


    suspend fun getWeather(cityName: String, units: String) : DataOrException<WeatherResponse, Boolean, Exception>  {

         return repository.getWeather(cityName,units)



    }


}