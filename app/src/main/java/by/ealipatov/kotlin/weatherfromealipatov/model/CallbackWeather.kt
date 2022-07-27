package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import java.io.IOException

interface CallbackWeather {
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}
