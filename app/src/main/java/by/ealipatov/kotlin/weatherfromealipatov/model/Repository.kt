package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

interface Repository {
    fun getAllWeather():List<Weather>

    fun getCityWeather(lat: Double, lon:Double):Weather

}