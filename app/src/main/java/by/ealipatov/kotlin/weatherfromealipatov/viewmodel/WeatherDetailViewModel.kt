package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.Dependencies.weatherRepository
import by.ealipatov.kotlin.weatherfromealipatov.model.RemoteRepository

class WeatherDetailViewModel: ViewModel() {

    val liveDataDetail: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }

    fun getLiveDataDetailRemoteRepository(weather: Weather): MutableLiveData<Weather> {
        //Выбираем уделенный репозиторий
        weatherRepository = RemoteRepository()
        //запрашиваем данные из удаленного репозитория
        liveDataDetail.postValue(weatherRepository.getCityWeather(weather))
        return liveDataDetail
    }
    //Для проверки работы на примере локального репозитория
//    fun getLiveDataDetailLocal(weather: Weather): MutableLiveData<Weather> {
//        liveDataDetail.postValue(weather)
//        return liveDataDetail
//    }
}
