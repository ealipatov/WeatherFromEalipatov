package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class RepositoryLocalImpl : Repository {
    override fun getAllCityWeather(): List<Weather> {
        return listOf(Weather())
    }

    override fun getCityWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}