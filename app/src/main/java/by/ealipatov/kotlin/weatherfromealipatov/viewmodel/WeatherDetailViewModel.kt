package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import java.io.IOException

class WeatherDetailViewModel(private val liveData: MutableLiveData<AppStateDetailViewModel> = MutableLiveData<AppStateDetailViewModel>()) :
    ViewModel() {

    lateinit var repositoryWeatherByCity: RepositoryWeatherByCity
    lateinit var repositoryWeatherSaveToDB: RepositoryWeatherSave

    fun getLiveData(): MutableLiveData<AppStateDetailViewModel> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {

        repositoryWeatherByCity = when (2) {
            1 -> {
                RepositoryWeatherByCityOkHttp()
            }
            2 -> {
                RepositoryRemoteServicesRetrofit()
            }
            3 -> {
                RepositoryWeatherByCityLoader()
            }
            4 -> {
                RepositoryRoomDB()
            }
            else -> {
                RepositoryWeatherByCityLocal()
            }
        }

        repositoryWeatherSaveToDB = when (0) {
            1 -> {
                RepositoryRoomDB()
            }
            else -> {
                RepositoryRoomDB()
            }
        }

    }

    fun getWeather(city: City) {
        choiceRepository()
        liveData.value = AppStateDetailViewModel.Loading
        repositoryWeatherByCity.getWeather(city,callback)
    }

    private val callback = object :CallbackWeather{
        override fun onResponse(weather: Weather) {
            liveData.postValue(AppStateDetailViewModel.Success(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateDetailViewModel.Error(e))
        }
    }

    fun isConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }

}
