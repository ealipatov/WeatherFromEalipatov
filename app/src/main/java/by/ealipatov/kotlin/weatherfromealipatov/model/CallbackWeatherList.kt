package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import java.io.IOException


interface CallbackWeatherList{
    fun onResponse(weather: List<Weather>)
    fun onFailure(e: IOException)
}