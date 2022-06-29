package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.model.Dependencies.weatherRepository
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppState.*
import java.util.*

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>(),
) : ViewModel() {

    /**
     * Получение (запрос к) liveData
     */
    fun getLiveData(): MutableLiveData<AppState> {
        //Выбираем репозиторий
        switchRepository()
        return liveData
    }

    /**
     * В зависимости от наличия подключения выбирается репозиторий (локальный/удаленный)
     */
    private fun switchRepository() {
        weatherRepository = if (isConnection()) {
            RemoteRepository()
        } else {
            LocalRepository()
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
        // Изначальный вариант((1..3).random() == 1)
        //val rand = Random(System.nanoTime())  (0..3).random(rand) == 1 Предложенный вариант преподавателем
        //Попробуем использовать такой рандом ((1..3).shuffled().last() == 1)
        if ((1..3).shuffled().last() == 1) {
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