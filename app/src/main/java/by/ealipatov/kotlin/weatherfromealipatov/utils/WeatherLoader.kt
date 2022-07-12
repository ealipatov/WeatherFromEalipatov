package by.ealipatov.kotlin.weatherfromealipatov.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.model.OnResponse
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import com.google.gson.Gson
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object WeatherLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun request(lat: Double, lon: Double, onResponse: OnResponse){
        try {
            val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            val myConnection: HttpURLConnection?

            myConnection = uri.openConnection() as HttpURLConnection
            myConnection.readTimeout = 5000
            myConnection.addRequestProperty(YANDEX_API_KEY,BuildConfig.WEATHER_API_KEY)
            Thread{
                try {
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                    onResponse.onResponse(weatherDTO)
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