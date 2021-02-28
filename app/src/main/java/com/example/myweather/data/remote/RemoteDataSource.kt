package com.example.myweather.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myweather.data.remote.response.weather.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class RemoteDataSource {




    companion object {
        private var city = "Jakarta"
        private var apiKey = ""
        private var units = "Metric"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getWeather(): LiveData<ApiResponse<WeatherResponse>> {

        val result = MutableLiveData<ApiResponse<WeatherResponse>>()
        val client = ApiConfig.getApiService().getWeather(
            city, apiKey, units
        )

        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.success(response.body())
                }
                else{
                    result.value = ApiResponse.empty("Empty response",response.body())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Timber.tag(getWeather().javaClass.simpleName).e(t.message.toString())
                result.value = ApiResponse.error(t.message.toString(),null)
            }

        })
        return result
    }
}