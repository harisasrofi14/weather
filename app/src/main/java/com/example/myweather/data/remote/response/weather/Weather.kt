package com.example.myweather.data.remote.response.weather

data class Weather(

    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)