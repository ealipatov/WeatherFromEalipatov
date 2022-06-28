package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppState.*

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>(),
) : ViewModel() {

    private lateinit var repositoryLocal: RepositoryLocal
    private lateinit var repositoryRetrofit: RepositoryRetrofit


    /***
     * Получение (запрос к) liveData
     */
    fun getLiveData(): MutableLiveData<AppState> {
        //Выбираем репозиторий
        switchRepository()
        return liveData
    }

    /***
     * В зависимости от наличия подключения выбирается репозиторий (локальный/удаленный)
     */
    fun switchRepository() {
        repositoryRetrofit = if (isConnection()) {
            RemoteRepository()
        } else {
            LocalRepository()
        }
        repositoryLocal = LocalRepository()
    }

    fun getWeatherListFor(location: Location) {
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

    /***
     * Отправка запроса
     */
    fun sendRequest(location: Location) {
        liveData.value = Loading
        if ((1..3).random() == 1) {
            liveData.postValue(Error(error = IllegalStateException("ой, что-то сломалось")))
        } else {
            liveData.postValue(SuccessList(repositoryLocal.getAllCityWeather(location)))
        }
    }

    /***
     * Проверка наличия подключения. Пока заглушка
     */
    fun isConnection(): Boolean {
        return false
    }
}