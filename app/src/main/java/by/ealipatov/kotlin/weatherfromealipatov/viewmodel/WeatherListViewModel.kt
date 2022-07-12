package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.model.Dependencies.weatherRepository
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppStateListViewModel.*

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppStateListViewModel> = MutableLiveData<AppStateListViewModel>(),
) : ViewModel() {

    /**
     * Получение (запрос к) liveData
     */
    fun getLiveData(): MutableLiveData<AppStateListViewModel> {
        //Выбираем репозиторий
        switchRepository()
        return liveData
    }

    /**
     * В зависимости от наличия подключения выбирается репозиторий (локальный/удаленный)
     */
    private fun switchRepository() {
        weatherRepository = if (isConnection()) {
            RepositoryRemoteServicesWeatherLoader()
        } else {
            RepositoryListCityLocal()
        }
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
            liveData.postValue(SuccessList(weatherRepository.getAllCityWeather(location)))
        }
    }

    /**
     * Проверка наличия подключения. Пока заглушка
     */
    private fun isConnection(): Boolean {
        return false
    }
}