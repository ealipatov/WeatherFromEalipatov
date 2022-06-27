package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class RepositoryRemoteImpl : RepositoryYandex {

    override fun getCityWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(200L)
        }.start()
        return Weather()
    }
}