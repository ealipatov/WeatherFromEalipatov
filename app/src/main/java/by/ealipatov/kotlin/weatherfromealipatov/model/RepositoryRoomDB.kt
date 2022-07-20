package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.MyApp
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.room.HistoryWeatherEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class RepositoryRoomDB : RepositoryWeatherByCity, RepositoryWeatherAll, RepositoryWeatherSave {

    override fun getWeather(city: City, callback: CallbackWeather) {
        GlobalScope.launch{
            callback.onResponse(MyApp.getHistoryWeatherDatabase().historyWeatherDAO()
                .getHistoryWeatherByCoordinates(city.lat, city.lon).let {
                    convertHistoryWeatherEntityToWeather(it).last()
                })
        }
    }

    override fun addWeather(weather: Weather) {
        GlobalScope.launch {
                MyApp.getHistoryWeatherDatabase().historyWeatherDAO()
                    .insertRoom(convertWeatherToEntity(weather))
            }
    }

    override fun getWeatherAll(callback: CallbackWeatherList) {
        GlobalScope.launch{
            callback.onResponse(
                convertHistoryWeatherEntityToWeather(
                    MyApp.getHistoryWeatherDatabase()
                        .historyWeatherDAO().getHistoryWeatherAll()
                )
            )
        }
    }

    private fun convertHistoryWeatherEntityToWeather(entityList: List<HistoryWeatherEntity>): List<Weather> {
        return entityList.map {
            Weather(
                City(it.country, it.name, it.lat, it.lon),
                it.temperature,
                it.feelsLike,
                it.condition,
                it.icon
            )
        }
    }

    private fun convertWeatherToEntity(weather: Weather): HistoryWeatherEntity {
        return HistoryWeatherEntity(
            0,
            weather.city.country,
            weather.city.name,
            weather.city.lat,
            weather.city.lon,
            weather.temperature,
            weather.feelsLike,
            weather.condition,
            weather.icon
        )
    }
}