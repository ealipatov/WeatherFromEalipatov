package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

fun interface RepositoryLocal {
    fun getAllCityWeather(location:Location):List<Weather>
}

fun interface RepositoryRetrofit {
    fun getCityWeather( lat: Double, lon: Double):Weather
}
