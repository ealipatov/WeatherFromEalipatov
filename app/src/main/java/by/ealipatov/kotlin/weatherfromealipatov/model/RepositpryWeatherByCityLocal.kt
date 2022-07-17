package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.getBelarusianCities
import by.ealipatov.kotlin.weatherfromealipatov.domain.getRussianCities
import by.ealipatov.kotlin.weatherfromealipatov.domain.getWorldCities

class RepositoryWeatherByCityLocal: RepositoryWeatherByCity {
        override fun getWeather(city: City, callback: CallbackWeather) {
            val list = getWorldCities().toMutableList()
            list.addAll(getRussianCities())
            list.addAll(getBelarusianCities())
            val response = list.filter { it.city.lat==city.lat&&it.city.lon==city.lon  }
            callback.onResponse((response.first()))
        }
}