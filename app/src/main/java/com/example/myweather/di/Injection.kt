package com.example.myweather.di

import android.content.Context
import com.example.myweather.data.WeatherRepository
import com.example.myweather.data.local.LocalDataSource
import com.example.myweather.data.local.room.WeatherRoomDatabase
import com.example.myweather.data.remote.RemoteDataSource
import com.example.myweather.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): WeatherRepository {
        val database = WeatherRoomDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.weatherDao())
        val appExecutors = AppExecutors()
        return WeatherRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}