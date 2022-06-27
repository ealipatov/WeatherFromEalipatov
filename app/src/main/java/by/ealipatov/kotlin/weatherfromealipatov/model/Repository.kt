package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

interface Repository {
    fun getAllCityWeather(): List<Weather>

    fun getCityWeather(lat: Double, lon: Double): Weather

}