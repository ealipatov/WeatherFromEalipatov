package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

sealed class AppStateDetailViewModel {
    data class Success(val weatherData: Weather) : AppStateDetailViewModel()
    data class Error(val error: Throwable) : AppStateDetailViewModel()
    object Loading : AppStateDetailViewModel()
}