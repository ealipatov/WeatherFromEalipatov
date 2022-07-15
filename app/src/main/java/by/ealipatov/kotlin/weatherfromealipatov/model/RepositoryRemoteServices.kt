package by.ealipatov.kotlin.weatherfromealipatov.model

fun interface RepositoryRemoteServices {
    fun getWeather(lat: Double, lon: Double,callback: CallbackResponse)
}