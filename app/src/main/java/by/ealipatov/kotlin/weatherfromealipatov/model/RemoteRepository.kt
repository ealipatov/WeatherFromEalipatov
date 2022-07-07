package by.ealipatov.kotlin.weatherfromealipatov.model

import android.os.Build
import androidx.annotation.RequiresApi
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.utils.WeatherLoader

class RemoteRepository : Repository {

    override fun getAllCityWeather(location: Location): List<Weather> {
        Thread {
            Thread.sleep(500L)
        }.start()
        Thread.currentThread().join() // проверить работу
        return listOf() // тут подумать
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getCityWeather(weather: Weather): Weather {
        WeatherLoader.request(
            weather.city.lat,
            weather.city.lon
        ) { weatherDTO ->
            weather.temperature = weatherDTO.fact.temp
            weather.feelsLike = weatherDTO.fact.feels_like
        }
        return Weather()
    }
}
