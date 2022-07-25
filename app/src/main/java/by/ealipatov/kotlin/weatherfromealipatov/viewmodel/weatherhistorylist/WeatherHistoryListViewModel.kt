package by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.CallbackWeatherList
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryRoomDB
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryWeatherAll
import java.io.IOException

class WeatherHistoryListViewModel(private val liveData: MutableLiveData<AppStateHistoryWeatherListViewModel> = MutableLiveData<AppStateHistoryWeatherListViewModel>()) :
    ViewModel() {

    private lateinit var repository: RepositoryWeatherAll

    fun getLiveData(): MutableLiveData<AppStateHistoryWeatherListViewModel> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = RepositoryRoomDB()
    }

    fun getAllHistory() {
        liveData.value = AppStateHistoryWeatherListViewModel.Loading
        repository.getWeatherAll(callback)
    }

    fun getSortedAllHistory() {
        liveData.value = AppStateHistoryWeatherListViewModel.Loading
        repository.getWeatherAll(callbackSorted)
    }

    private val callback = object : CallbackWeatherList {
        override fun onResponse(weather: List<Weather>) {
            liveData.postValue(AppStateHistoryWeatherListViewModel.Success(weather))
        }
        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateHistoryWeatherListViewModel.Error(e))
        }
    }

    private val callbackSorted = object : CallbackWeatherList {
        override fun onResponse(weather: List<Weather>) {
            liveData.postValue(AppStateHistoryWeatherListViewModel.Success(weather.sortedBy { it.city.name }))
        }
        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateHistoryWeatherListViewModel.Error(e))
        }
    }

}