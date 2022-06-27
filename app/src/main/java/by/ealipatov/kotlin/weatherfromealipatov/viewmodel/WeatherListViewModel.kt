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

    private lateinit var repository: Repository
    private lateinit var repositoryYandex: RepositoryYandex

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
        repositoryYandex = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
        repository = RepositoryLocalImpl()
    }


    fun getWeatherListFor(location: Location){
        when (location){
            Location.Belarus -> {sendRequest(Location.Belarus)}
            Location.Russian -> {sendRequest(Location.Russian)}
            Location.World -> {sendRequest(Location.World)}
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
            //liveData.postValue(Success(repositoryYandex.getCityWeather(55.755826, 37.617299900000035)))
            liveData.postValue(SuccessList(repository.getAllCityWeather(location)))
        }


    }

    /***
     * Проверка наличия подключения. Пока заглушка
     */
    fun isConnection(): Boolean {
        return false
    }

}