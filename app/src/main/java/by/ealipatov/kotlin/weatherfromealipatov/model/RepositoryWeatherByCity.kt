package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.City

fun interface RepositoryWeatherByCity {
    fun getWeather(city: City,callback: CallbackWeather)
}