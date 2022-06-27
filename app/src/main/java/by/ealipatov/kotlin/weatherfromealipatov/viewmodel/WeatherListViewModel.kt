package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (val liveData: MutableLiveData<Any> = MutableLiveData<Any>()) : ViewModel() {

}