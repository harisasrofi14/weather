package com.example.myweather.data

import androidx.lifecycle.LiveData
import com.example.myweather.data.local.LocalDataSource
import com.example.myweather.data.local.entity.Weather
import com.example.myweather.data.remote.ApiResponse
import com.example.myweather.data.remote.RemoteDataSource
import com.example.myweather.data.remote.response.weather.WeatherResponse
import com.example.myweather.utils.AppExecutors
import com.example.myweather.vo.Resource

class WeatherRepository private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : WeatherDataSource {
    companion object {
        @Volatile
        private var instance: WeatherRepository? = null
        fun getInstance(
                remoteData: RemoteDataSource,
                localData: LocalDataSource,
                appExecutors: AppExecutors
        ): WeatherRepository =
                instance ?: synchronized(this) {
                    instance ?: WeatherRepository(remoteData, localData, appExecutors)
                }
    }

    override fun getWeather(): LiveData<Resource<Weather>> {
        return object : NetworkBoundResource<Weather, WeatherResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Weather> {
                return localDataSource.getWeather()
            }

            override fun shouldFetch(data: Weather?): Boolean {

                return data == null || data.modifiedDate < System.currentTimeMillis()
            }

            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> {
                return remoteDataSource.getWeather()
            }

            override fun saveCallResult(data: WeatherResponse) {

                val newData = Weather(
                        data.id,
                        data.main.temp.toString(),
                        data.weather[0].main,
                        data.main.temp_min.toString(),
                        data.main.temp_max.toString(),
                        data.name,
                        data.sys.sunrise.toLong(),
                        data.sys.sunset.toLong(),
                        data.wind.speed.toString(),
                        data.main.pressure,
                        data.main.humidity,
                        "Haris",
                        System.currentTimeMillis()
                )
                localDataSource.insertWeather(newData)

            }
        }.asLiveData()
    }
}