package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import java.io.IOException

class WeatherDetailViewModel(private val liveData: MutableLiveData<AppStateDetailViewModel> = MutableLiveData<AppStateDetailViewModel>()) :
    ViewModel() {

    lateinit var repository: RepositoryRemoteServices

    fun getLiveData(): MutableLiveData<AppStateDetailViewModel> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {

        repository = when (1) {
            1 -> {
                RepositoryRemoteServicesOkHttp()
            }
            2 -> {
                RepositoryRemoteServicesRetrofit()
            }
            else -> {
                RepositoryRemoteServicesWeatherLoader()
            }
        }
    }

    fun getWeather(lat: Double, lon: Double) {
        choiceRepository()
        liveData.value = AppStateDetailViewModel.Loading
        repository.getWeather(lat, lon,callback)
    }

    private val callback = object :CallbackResponse{
        override fun onResponse(weatherDTO: WeatherDTO) {
            liveData.postValue(AppStateDetailViewModel.Success(weatherDTO))
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
