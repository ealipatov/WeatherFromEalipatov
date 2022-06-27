package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

//sealed - классы наследники не будут являться "детьми" родительского класса (почитать)
sealed class AppState {
    data class Success (val weatherData: Weather) : AppState()
    data class Error (val error: Throwable) : AppState()
    object Loading : AppState()
}