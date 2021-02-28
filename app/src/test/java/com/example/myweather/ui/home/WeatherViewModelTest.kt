package com.example.myweather.ui.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myweather.data.WeatherRepository
import com.example.myweather.data.local.entity.Weather
import com.example.myweather.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private var dummyWeather = Weather()


    @get:Rule
    var instantTaskExecutors = InstantTaskExecutorRule()

    @Mock
    private lateinit var weatherRepository: WeatherRepository


    @Mock
    private lateinit var observer: Observer<Resource<Weather>>


    @Before
    fun setUp() {
        viewModel = WeatherViewModel(weatherRepository)
        dummyWeather = Weather(
            123,
            "30",
            "Cloudy",
            "24",
            "31",
            "Jakarta",
            1614466709,
            1614510735,
            "4.63",
            1010,
            65,
            "Haris")
    }
    @Test
    fun getWeather(){
        val dummyWeather = Resource.success(dummyWeather)
        val weather = MutableLiveData<Resource<Weather>>()
        weather.value = dummyWeather
        `when`(weatherRepository.getWeather()).thenReturn(weather)
        val weatherEntity = viewModel.getWeather()
        verify(weatherRepository).getWeather()
        assertNotNull(weatherEntity)

        viewModel.getWeather().observeForever(observer)
        verify(observer).onChanged(dummyWeather)
    }
}