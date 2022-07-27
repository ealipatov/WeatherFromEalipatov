package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import java.io.IOException

interface CallbackCityCoordinates {
    fun onResponse(city: City)
    fun onFailure(e: IOException)
}