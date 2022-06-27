package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class RepositoryRemoteImpl : Repository {
    override fun getAllCityWeather(): List<Weather> {
        Thread {
            Thread.sleep(500L)
        }.start()

        return listOf(Weather())
    }

    override fun getCityWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(200L)
        }.start()
        return Weather()
    }
}