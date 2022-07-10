package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherDetailViewModel

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding: FragmentWeatherDetailBinding
        get() {
            return _binding!!
        }

    lateinit var viewModelDetail: WeatherDetailViewModel

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelDetail = ViewModelProvider(this).get((WeatherDetailViewModel::class.java))

        val weather = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)

        //Не разобрался как во вьюмодель не передавать текущую погроду.

        if (weather != null) {
            //Зависает программа на выборе списка городов при отсутствии подключения.
            viewModelDetail.getLiveDataDetailRemoteRepository(weather)
            // viewModelDetail.getLiveDataDetailLocal(weather)
        }

        val observer = Observer<Weather> {
            renderData(it)
        }
        viewModelDetail.liveDataDetail.observe(viewLifecycleOwner, observer)

    }

    private fun renderData(weather: Weather) {
        //Используем переменную функционального типа, для преобразования координат в строку.
        val coordinates = fun(lat: Double, lon: Double) = "$lat / $lon"
        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = coordinates(weather.city.lat, weather.city.lat)
            weatherCondition.text = translate(weather.condition)

        }
    }

    private fun translate(f: String): String {
        return when (f) {
            "clear" -> "Ясно"
            "partly_cloudy" -> "Малооблачно"
            "cloudy" -> "Облачно с прояснениями"
            "overcast" -> "Пасмурно"
            "drizzle" -> "Морось"
            "light_rain" -> "Небольшой дождь"
            "rain" -> "Дождь"
            "moderate_rain" -> "Умеренно сильный дождь"
            "heavy_rain" -> "Сильный дождь"
            "continuous_heavy_rain" -> "Длительный сильный дождь"
            "showers" -> "Ливень"
            "wet_snow" -> "Дождь со снегом"
            "light_snow" -> "Небольшой снег"
            "snow" -> "Снег"
            "snow_showers" -> "Снегопад"
            "hail" -> "Град"
            "thunderstorm" -> "Гроза"
            "thunderstorm_with_rain" -> "Дождь с грозой"
            "thunderstorm_with_hail" -> "Гроза с градом"
            else -> {
                f
            }
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "BUNDLE_WEATHER_EXTRA"

        //Исправление кода согласно замечанию преподавателя
        fun newInstance(weather: Weather) = WeatherDetailFragment().also {
            it.arguments = Bundle().apply { putParcelable(BUNDLE_WEATHER_EXTRA, weather) }

        }
    }
}