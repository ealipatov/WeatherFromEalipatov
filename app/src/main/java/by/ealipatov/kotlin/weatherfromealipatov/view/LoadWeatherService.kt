package by.ealipatov.kotlin.weatherfromealipatov.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.*
import com.google.gson.Gson
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LoadWeatherService : IntentService("LoadWeatherService") {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {City ->
                try {

                    val uri =
                        URL("https://api.weather.yandex.ru/v2/informers?lat=${City.lat}&lon=${City.lon}")

                    Thread {
                        var myConnection: HttpsURLConnection? = null
                        myConnection = uri.openConnection() as HttpsURLConnection
                        try {
                            myConnection.readTimeout = 5000
                            myConnection.addRequestProperty(
                                YANDEX_API_KEY,
                                BuildConfig.WEATHER_API_KEY
                            )

                            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                            val weatherDTO =
                                Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                                putExtra(BUNDLE_WEATHER_DTO_KEY, weatherDTO)
                                action = SERVER_RESPONSE
                            })

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
    }
}