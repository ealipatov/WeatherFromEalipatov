package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.Repository
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryLocalImpl
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryRemoteImpl
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppState.*

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>(),
) : ViewModel() {

    private lateinit var repository: Repository

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
        repository = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
    }

    /***
     * Отправка запроса
     */
    fun sendRequest() {
        liveData.value = Loading
        if ((1..3).random() == 1) {
            liveData.postValue(Error(error = IllegalStateException("ой, что-то сломалось")))
        } else {
            liveData.postValue(Success(repository.getCityWeather(55.755826, 37.617299900000035)))
        }


    }

    /***
     * Проверка наличия подключения. Пока заглушка
     */
    fun isConnection(): Boolean {
        return false
    }

}