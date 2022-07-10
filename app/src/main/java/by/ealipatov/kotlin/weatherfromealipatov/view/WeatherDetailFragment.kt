package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO
import by.ealipatov.kotlin.weatherfromealipatov.utils.WeatherLoader

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding: FragmentWeatherDetailBinding
        get() {
            return _binding!!
        }

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

//        override fun onActivityCreated(savedInstanceState: Bundle?) {
//            super.onActivityCreated(savedInstanceState)
//            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//           
//        }

        val weather = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)


        weather?.let { weatherLocal ->

            WeatherLoader.request(
                weatherLocal.city.lat,
                weatherLocal.city.lon
            ) { weatherDTO ->
                bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
            }
        }
    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        requireActivity().runOnUiThread{
            renderData(weatherLocal.apply {
                weatherLocal.feelsLike = weatherDTO.fact.feels_like
                weatherLocal.temperature = weatherDTO.fact.temp
                weatherLocal.condition = weatherDTO.fact.condition.replace("-","_")

            })
        }
    }

    //Проверяем работу with
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
    private fun translate(f:String):String{
      return when(f){
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
          else -> {f}
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