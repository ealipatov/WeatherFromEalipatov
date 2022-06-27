package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.R
import java.io.IOException
import java.lang.Thread.sleep

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) : ViewModel() {

        fun sendRequest(){
            liveData.value = AppState.Loading

            Thread {
                sleep(2000L)
                //postValue - передача значений из другого потока
                liveData.postValue(AppState.Success(Any()))
            }.start()


        }

}