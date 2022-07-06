package by.ealipatov.kotlin.weatherfromealipatov.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.model.DTO.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object WeatherLoader {// TODO HW 5 try catch

    @RequiresApi(Build.VERSION_CODES.N)
    fun request(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        try {
            val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            var myConnection: HttpURLConnection? = null

            Thread {
                try {
                    myConnection = uri.openConnection() as HttpURLConnection
                    myConnection?.readTimeout = 5000
                    myConnection?.addRequestProperty(
                        "X-Yandex-API-Key",
                        BuildConfig.WEATHER_API_KEY
                    )
                    val reader = BufferedReader(InputStreamReader(myConnection?.inputStream))
                    val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                    block(weatherDTO)
                } catch (e: Exception) {
                    Log.e("***", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    myConnection?.disconnect()
                }

            }.start()
        } catch (e: MalformedURLException) {
            Log.e("***", "Fail URI", e)
            e.printStackTrace()
        }
    }
}