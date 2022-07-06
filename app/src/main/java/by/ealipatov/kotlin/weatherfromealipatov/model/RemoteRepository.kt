package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class RemoteRepository : Repository {

    override fun getAllCityWeather(location: Location): List<Weather> {
        Thread {
            Thread.sleep(500L)
        }.start()
        Thread.currentThread().join() // проверить работу
        return listOf() // тут подумать
    }

    override fun getCityWeather(lat: Double, lon: Double): Weather {
            return Weather()
        }

}