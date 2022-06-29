package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class RemoteRepository : Repository {

    override fun getAllCityWeather(location: Location): List<Weather> {
        Thread {
            Thread.sleep(500L)
        }.start()
        return listOf() // тут подумать
    }

    override fun getCityWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(200L)
        }.start()
        return Weather()
    }

}