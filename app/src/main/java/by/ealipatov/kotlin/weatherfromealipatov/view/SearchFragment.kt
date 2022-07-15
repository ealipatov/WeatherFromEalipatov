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
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentSearchBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.geo.CityCoordinates
import com.google.gson.Gson
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() {
            return _binding!!
        }

    private lateinit var weatherSearchCity: Weather

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

        binding.searchSendBtn.setOnClickListener() {
            getCityCoordinates(binding.cityName.text.toString())
        }
    }

    private fun getCityCoordinates(name: String) {
        try {
            val uri =
                URL("https://geocode-maps.yandex.ru/1.x/?apikey=${BuildConfig.GEOCODER_API_KEY}&format=json&geocode=${name}&results=1")
            val myConnection: HttpURLConnection?

            myConnection = uri.openConnection() as HttpURLConnection
            myConnection.readTimeout = 5000

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val cityGeocode = Gson().fromJson((reader), CityCoordinates::class.java)
                    Handler(Looper.getMainLooper()).post {
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
                        weatherSearchCity = Weather(searchCity, 0, 0)
                        onSearchClick(weatherSearchCity)

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

    private fun onSearchClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).replace(
            R.id.container, WeatherDetailFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}