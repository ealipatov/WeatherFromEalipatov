package by.ealipatov.kotlin.weatherfromealipatov.model

import android.util.Log
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.YANDEX_API_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.converterWeatherDTOWithCityToWeather
import com.google.gson.Gson
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RepositoryWeatherByCityLoader : RepositoryWeatherByCity {

    override fun getWeather(city: City, callback: CallbackWeather) {
        try {
            val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
            val myConnection: HttpURLConnection?
            myConnection = uri.openConnection() as HttpURLConnection

            Thread {
                try {
                    myConnection.readTimeout = 5000
                    myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
                    callback.onResponse(converterWeatherDTOWithCityToWeather(weatherDTO,city))
                } catch (e: IOException) {
                    Log.e("***", "Fail connection", e)
                    e.printStackTrace()
                } catch (e: JSONException) {
                    Log.e("***", "Fail format Json", e)
                    e.printStackTrace()
                } finally {
                    myConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("***", "Fail URI", e)
            e.printStackTrace()
        }
    }
}

