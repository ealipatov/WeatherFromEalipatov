package by.ealipatov.kotlin.weatherfromealipatov.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.BUNDLE_CITY_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.BUNDLE_WEATHER_DTO_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.SERVER_RESPONSE

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding: FragmentWeatherDetailBinding
        get() {
            return _binding!!
        }

    //Урок 6 Настроим бродкастрессивер
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)
                    ?.let { weatherDTO ->
                        bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                    }
            }
        }
    }

//    lateinit var viewModelDetail: WeatherDetailViewModel

    //Урок 6
    lateinit var weatherLocal: Weather

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

        val weather = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        //Урок 6.
        weather?.let { weatherLocal ->
            this.weatherLocal = weatherLocal

            //Урок 6. Вызовем локальный бродкастрессивер и будем слушать "ответ сервера"
            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                receiver,
                IntentFilter(SERVER_RESPONSE)
            )

            //Урок 6. Запустим сервис получения данных с сервера
            requireActivity().startService(
                Intent(
                    requireContext(),
                    LoadWeatherService::class.java
                ).apply {
                    putExtra(BUNDLE_CITY_KEY, weatherLocal?.city)
                })
        }
    }

    //Урок 6
    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        renderData(weatherLocal.apply {
            weatherLocal.feelsLike = weatherDTO.fact.feels_like
            weatherLocal.temperature = weatherDTO.fact.temp
        })
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
}