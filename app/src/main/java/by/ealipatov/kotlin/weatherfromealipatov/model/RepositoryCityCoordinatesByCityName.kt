package by.ealipatov.kotlin.weatherfromealipatov.model

fun interface RepositoryCityCoordinatesByCityName {
    fun getCityCoordinates(cityName: String, callback: CallbackCityCoordinates)
}