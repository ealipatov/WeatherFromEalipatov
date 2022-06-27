package by.ealipatov.kotlin.weatherfromealipatov.model


import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

fun interface Repository {
    fun getAllCityWeather(location: Location): List<Weather>
}

fun interface RepositoryYandex {
    fun getCityWeather( lat: Double, lon: Double):Weather
}