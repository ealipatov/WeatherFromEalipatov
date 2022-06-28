package by.ealipatov.kotlin.weatherfromealipatov.view

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}