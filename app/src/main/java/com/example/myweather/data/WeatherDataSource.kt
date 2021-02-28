package com.example.myweather.data

import androidx.lifecycle.LiveData
import com.example.myweather.data.local.entity.Weather
import com.example.myweather.vo.Resource

interface WeatherDataSource {
    fun getWeather(): LiveData<Resource<Weather>>
}