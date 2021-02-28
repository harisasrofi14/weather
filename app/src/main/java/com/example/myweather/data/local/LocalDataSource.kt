package com.example.myweather.data.local

import androidx.lifecycle.LiveData
import com.example.myweather.data.local.entity.Weather
import com.example.myweather.data.local.room.WeatherDao

class LocalDataSource private constructor(private  val mWeatherDao : WeatherDao){

    companion object {
        private var INSTANCE : LocalDataSource? = null

        fun getInstance(weatherDao: WeatherDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(weatherDao)
    }

    fun getWeather() : LiveData<Weather> = mWeatherDao.getWeather()

    fun insertWeather(weather: Weather) = mWeatherDao.insertWeather(weather)

}