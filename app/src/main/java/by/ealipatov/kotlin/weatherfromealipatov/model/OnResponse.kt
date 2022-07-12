package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO

fun interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}