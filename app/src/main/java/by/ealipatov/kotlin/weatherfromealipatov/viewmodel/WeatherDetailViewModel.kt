package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.Dependencies.weatherRepository
import by.ealipatov.kotlin.weatherfromealipatov.model.RemoteRepository

class WeatherDetailViewModel (private val liveDataDetailViewModel: MutableLiveData<Weather> = MutableLiveData<Weather>()) :
    ViewModel() {

        /**
         * Получение (запрос к) liveData
         */
        fun getLiveDataDetail(): MutableLiveData<Weather> {
            weatherRepository = RemoteRepository()
            return liveDataDetailViewModel
        }

        /**
         * Отправка запроса
         */
        fun sendRequestDetail(weather: Weather) {
            liveDataDetailViewModel.postValue(
                        weatherRepository.getCityWeather(weather)
                    )
        }
}