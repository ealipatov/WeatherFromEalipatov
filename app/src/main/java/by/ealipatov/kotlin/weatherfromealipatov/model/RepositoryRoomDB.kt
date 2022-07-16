package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.MyApp

class RepositoryRoomDB : RepositoryRemoteServices {
    override fun getWeather(lat: Double, lon: Double, callback: CallbackResponse) {
        MyApp.getHistoryWeatherDatabase().historyWeatherDAO()
            .getHistoryWeatherByCoordinates(lat, lon)
    }
}