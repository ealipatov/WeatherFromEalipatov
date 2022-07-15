package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO

sealed class AppStateDetailViewModel {
    data class Success(val weatherData: WeatherDTO) : AppStateDetailViewModel()
    data class Error(val error: Throwable) : AppStateDetailViewModel()
    object Loading : AppStateDetailViewModel()
}