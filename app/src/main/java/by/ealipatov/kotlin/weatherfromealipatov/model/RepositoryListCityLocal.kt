package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.domain.getBelarusianCities
import by.ealipatov.kotlin.weatherfromealipatov.domain.getRussianCities
import by.ealipatov.kotlin.weatherfromealipatov.domain.getWorldCities

class RepositoryListCityLocal : RepositoryListCity {
    override fun getAllCityWeather(location: Location): List<Weather> {
        return when (location) {
            Location.Belarus -> getBelarusianCities()
            Location.Russian -> getRussianCities()
            Location.World -> getWorldCities()
        }
    }
}
