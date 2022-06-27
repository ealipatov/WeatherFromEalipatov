package by.ealipatov.kotlin.weatherfromealipatov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.ealipatov.kotlin.weatherfromealipatov.model.Repository
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryLocalImpl
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryRemoteImpl
import java.lang.Thread.sleep

//Создадим liveData сразу в конструкторе
class WeatherListViewModel
    (
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>(),
    var repository: Repository
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppState>{
        switchRepository()
        return liveData
    }

    private fun switchRepository(){
        repository = if(isConnection()){
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
    }


    fun sendRequest() {
        liveData.value = AppState.Loading

        Thread {
            sleep(2000L)
            //postValue - передача значений из другого потока
            liveData.postValue(AppState.Success(Any()))
        }.start()
    }

    fun isConnection(): Boolean{
        return false
    }

}