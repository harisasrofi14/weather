package com.example.myweather.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.BuildConfig
import com.example.myweather.R
import com.example.myweather.viewmodel.ViewModelFactory
import com.example.myweather.vo.Status
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val factory = ViewModelFactory.getInstance(applicationContext)
        val viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]

        getData(viewModel)
        swipe_layout.setOnRefreshListener {
            getData(viewModel)
            swipe_layout.isRefreshing = false
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getData(viewModel: WeatherViewModel) {
        Timber.d("Start getData")
        viewModel.getWeather().observe(this, Observer { weather ->
            if (weather != null) {
                when (weather.status) {
                    Status.LOADING -> {
                        shimmerFrameLayout.startShimmerAnimation()
                        shimmerFrameLayout.visibility = View.VISIBLE
                        wrapper_content.visibility = View.GONE
                    }
                    Status.SUCCESS -> if (weather.data != null) {
                        val dateSunrise = weather.data.sunrise.let { Date(it * 1000) }
                        val dateSunset = weather.data.sunset.let { Date(it * 1000) }
                        val dateLastUpdate = weather.data.modifiedDate.let { Date(it) }
                        tv_city.text = weather.data.name
                        tv_haze.text = weather.data.main
                        tv_temperature.text = String.format(
                            resources.getString(R.string.temperature),
                            weather.data.temp
                        )
                        tv_max_temperature.text = String.format(
                            resources.getString(R.string.maxTemperature),
                            weather.data.max_temp
                        )
                        tv_min_temperature.text = String.format(
                            resources.getString(R.string.minTemperature),
                            weather.data.min_temp
                        )
                        tv_sunrise_value.text = formatTime(dateSunrise)
                        tv_created_by_value.text = weather.data.createdBy
                        tv_sunset_value.text = formatTime(dateSunset)
                        tv_last_update.text = String.format(
                            resources.getString(R.string.lastUpdate), formatTime2(
                                dateLastUpdate
                            ).toString()
                        )
                        tv_wind_value.text = weather.data.wind
                        tv_pressure_value.text = weather.data.pressure.toString()
                        tv_humidity_value.text = weather.data.humidity.toString()
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
                        wrapper_content.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
                        wrapper_content.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, weather.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })

    }

    private fun formatTime(dateObject: Date): String? {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return timeFormat.format(dateObject)
    }

    private fun formatTime2(dateObject: Date): String? {
        val timeFormat = SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault())
        return timeFormat.format(dateObject)
    }
}