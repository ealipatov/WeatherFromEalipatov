package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

fun interface RepositoryListCity {
    fun getAllCityWeather(location: CountryName): List<Weather>
}
