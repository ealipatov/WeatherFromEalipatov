package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

//sealed - классы наследники не будут являться "детьми" родительского класса (почитать)
sealed class AppStateListViewModel {
    data class Success(val weatherData: Weather) : AppStateListViewModel()
    data class SuccessList(val weatherList: List<Weather>) : AppStateListViewModel()
    data class Error(val error: Throwable) : AppStateListViewModel()
    object Loading : AppStateListViewModel()
}