package by.ealipatov.kotlin.weatherfromealipatov.viewmodel.citieslist

import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

sealed class AppStateCitiesListViewModel {
    data class Success(val weatherList: List<Weather>) : AppStateCitiesListViewModel()
    data class Error(val error: Throwable) : AppStateCitiesListViewModel()
    object Loading : AppStateCitiesListViewModel()
}