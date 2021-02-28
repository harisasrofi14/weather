package com.example.myweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.data.WeatherRepository
import com.example.myweather.data.local.entity.Weather
import com.example.myweather.vo.Resource

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    fun getWeather(): LiveData<Resource<Weather>> = weatherRepository.getWeather()
}