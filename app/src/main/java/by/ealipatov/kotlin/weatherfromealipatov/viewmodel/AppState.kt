package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

//sealed - классы наследники не будут являться "детьми" родительского класса
sealed class AppState {
    data class Success (val weatherData: Any) : AppState()
    data class Error (val error: Throwable) : AppState()
    object Loading : AppState()
}