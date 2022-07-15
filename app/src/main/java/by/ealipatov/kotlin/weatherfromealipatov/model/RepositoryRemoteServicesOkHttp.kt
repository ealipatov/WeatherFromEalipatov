package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.YANDEX_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryRemoteServicesOkHttp: RepositoryRemoteServices {
    override fun getWeather(lat: Double, lon: Double, callback: CallbackResponse) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO =
                            Gson().fromJson((responseString), WeatherDTO::class.java)
                        callback.onResponse(weatherDTO)
                    }
                } else {
                    // TODO HW callback.on??? 403 404
                    callback.onFailure(IOException("403 404"))
                }
            }
        })
    }

}