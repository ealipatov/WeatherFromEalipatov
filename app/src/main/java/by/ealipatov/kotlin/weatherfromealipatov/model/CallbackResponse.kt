package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import java.io.IOException

interface CallbackResponse {
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: IOException)
}