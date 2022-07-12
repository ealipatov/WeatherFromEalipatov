package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.YANDEX_API_KEY
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.io.IOException

interface WeatherAPI {

    @GET("/v2/informers")
    fun getWeather(
        @Header(YANDEX_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ):Call<WeatherDTO>
}

class RepositoryRemoteServicesRetrofit: RepositoryRemoteServices {
    override fun getWeather(lat: Double, lon: Double, callback: CallbackResponse) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://api.weather.yandex.ru")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(WeatherAPI::class.java)
        api.getWeather(BuildConfig.WEATHER_API_KEY,lat,lon).enqueue(object : Callback<WeatherDTO> {
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponse(response.body()!!)
                }else {
                    // TODO HW callback.on??? 403 404
                    callback.onFailure(IOException("403 404"))
                }
            }
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                callback.onFailure(t as IOException) //костыль
            }
        })
    }
}