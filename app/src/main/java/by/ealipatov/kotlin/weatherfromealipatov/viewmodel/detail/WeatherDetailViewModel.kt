package by.ealipatov.kotlin.weatherfromealipatov.viewmodel.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.MyApp.Companion.getMyApp
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

        if (isConnection(getMyApp().applicationContext)){
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
        } else {
            repositoryWeatherByCity = when (1) {
                1 -> {
                    RepositoryRoomDB()
                }
                2 -> {
                    RepositoryWeatherByCityLocal()
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

    }

    fun getWeather(city: City) {
        liveData.value = AppStateDetailViewModel.Loading
        repositoryWeatherByCity.getWeather(city,callback)
    }

    private val callback = object :CallbackWeather{
        override fun onResponse(weather: Weather) {
            if (isConnection(getMyApp().applicationContext))
                repositoryWeatherSaveToDB.addWeather(weather) // Добавим погоду в БД
            liveData.postValue(AppStateDetailViewModel.Success(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateDetailViewModel.Error(e))
        }
    }

    @Suppress("DEPRECATION")
    fun isConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }

}
