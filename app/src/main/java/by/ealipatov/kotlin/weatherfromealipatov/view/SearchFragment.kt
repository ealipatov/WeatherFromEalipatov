package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentSearchBinding
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

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() {
            return _binding!!
        }

    private lateinit var searchCity: City

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchSendBtn.setOnClickListener(){
            getCityCoordinates(binding.cityName.toString())
        }
    }

    private fun getCityCoordinates(name: String) {
        try {
            val uri = URL("https://geocode-maps.yandex.ru/1.x/?apikey=${BuildConfig.GEOCODER_API_KEY}&format=json&geocode=${name}&results=1")
            val myConnection: HttpURLConnection?

            myConnection = uri.openConnection() as HttpURLConnection
            myConnection.readTimeout = 5000

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val cityGeocode = Gson().fromJson((reader), CityCoordinates::class.java)
                    Handler(Looper.getMainLooper()).post {
                        searchCity.name = cityGeocode.response.GeoObjectCollection.featureMember[0].GeoObject.name
                        val parts = cityGeocode.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(" ")
                        searchCity.lat = parts[0].toDouble()
                        searchCity.lat = parts[1].toDouble()

                    }
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