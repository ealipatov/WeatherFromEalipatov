package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) : ViewModel() {

        fun sendRequest(){
            liveData.value = AppState.Loading
            //liveData.value = AppState.Success(Any())

            Thread {
                sleep(2000L)
                //postValue - передача значений из другого потока
                liveData.postValue(AppState.Success(Any()))
            }.start()

        }

}