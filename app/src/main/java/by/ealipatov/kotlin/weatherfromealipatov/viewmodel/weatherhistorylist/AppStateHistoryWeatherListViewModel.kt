package by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

sealed class AppStateHistoryWeatherListViewModel{
    data class Success(val weatherList: List<Weather>) : AppStateHistoryWeatherListViewModel()
    data class Error(val error: Throwable) : AppStateHistoryWeatherListViewModel()
    object Loading : AppStateHistoryWeatherListViewModel()
}
