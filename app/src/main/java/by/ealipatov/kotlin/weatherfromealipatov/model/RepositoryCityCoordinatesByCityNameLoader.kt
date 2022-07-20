package by.ealipatov.kotlin.weatherfromealipatov.model

import android.util.Log
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.model.geo.CityCoordinates
import com.google.gson.Gson
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RepositoryCityCoordinatesByCityNameLoader: RepositoryCityCoordinatesByCityName {

    override fun getCityCoordinates(cityName: String, callback: CallbackCityCoordinates) {
        try {
            val uri =
                URL("https://geocode-maps.yandex.ru/1.x/?apikey=${BuildConfig.GEOCODER_API_KEY}&format=json&geocode=${cityName}&results=1")
            val myConnection: HttpURLConnection?

            myConnection = uri.openConnection() as HttpURLConnection
            myConnection.readTimeout = 5000

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val cityGeocode = Gson().fromJson((reader), CityCoordinates::class.java)
                    val parts =
                        cityGeocode.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(
                            " "
                        )
                    val searchCity = City(
                        cityGeocode.response.GeoObjectCollection.featureMember[0]
                            .GeoObject.metaDataProperty.GeocoderMetaData.AddressDetails.Country.CountryName,
                        cityGeocode.response.GeoObjectCollection.featureMember[0].GeoObject.name,
                        parts[1].toDouble(),
                        parts[0].toDouble()
                    )
                    callback.onResponse(searchCity)

                } catch (e: IOException) {
                    callback.onFailure(IOException("403 404"))
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
