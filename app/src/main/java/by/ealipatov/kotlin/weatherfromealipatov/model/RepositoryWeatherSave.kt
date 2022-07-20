package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

fun interface RepositoryWeatherSave {
    fun addWeather(weather: Weather)
}