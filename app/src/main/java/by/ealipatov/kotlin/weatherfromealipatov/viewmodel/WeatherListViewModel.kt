package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.model.Dependencies.weatherRepository
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppStateListViewModel.*

class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppStateListViewModel> = MutableLiveData<AppStateListViewModel>(),
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppStateListViewModel> {
        switchRepository()
        return liveData
    }

    private fun switchRepository() {
        //Пока один репозиторий
        weatherRepository = RepositoryListCityLocal()
    }

    /**
     * Функция получения списка городов, согласно выбраной страны
     */
    fun getWeatherListForLocation(location: Location) {
        when (location) {
            Location.Belarus -> {
                sendRequest(Location.Belarus)
            }
            Location.Russian -> {
                sendRequest(Location.Russian)
            }
            Location.World -> {
                sendRequest(Location.World)
            }
        }
    }

    /**
     * Отправка запроса
     */
    private fun sendRequest(location: Location) {
        liveData.value = Loading
        if ((1..3).shuffled().last() == 4) {
            liveData.postValue(Error(error = IllegalStateException("ой, что-то сломалось")))
        } else {
            liveData.postValue(Success(weatherRepository.getAllCityWeather(location)))
        }
    }

}