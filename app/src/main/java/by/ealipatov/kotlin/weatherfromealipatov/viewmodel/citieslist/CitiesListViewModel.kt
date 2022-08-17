package by.ealipatov.kotlin.weatherfromealipatov.viewmodel.citieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.*
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.citieslist.AppStateCitiesListViewModel.*

class CitiesListViewModel
    (
    private val liveData: MutableLiveData<AppStateCitiesListViewModel> = MutableLiveData<AppStateCitiesListViewModel>(),
) : ViewModel() {

    lateinit var repositoryCitiesList: RepositoryListCity

    fun getLiveData(): MutableLiveData<AppStateCitiesListViewModel> {
        switchRepository()
        return liveData
    }

    private fun switchRepository() {
        repositoryCitiesList = RepositoryListCityLocal()
    }

    /**
     * Функция получения списка городов, согласно выбраной страны
     */
    fun getWeatherListForLocation(location: CountryName) {
        when (location) {
            CountryName.Belarus -> {
                sendRequest(CountryName.Belarus)
            }
            CountryName.Russian -> {
                sendRequest(CountryName.Russian)
            }
            CountryName.World -> {
                sendRequest(CountryName.World)
            }
        }
    }

    /**
     * Отправка запроса
     */
    private fun sendRequest(location: CountryName) {
        liveData.value = Loading
        if ((1..3).shuffled().last() == 4) {
            liveData.postValue(Error(error = IllegalStateException("ой, что-то сломалось")))
        } else {
            liveData.postValue(Success(repositoryCitiesList.getAllCityWeather(location)))
        }
    }

}