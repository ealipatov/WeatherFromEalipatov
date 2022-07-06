package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run { getParcelable<Weather>(BUNDLE_WEATHER_EXTRA) }?.let { weather ->
            renderData(weather)
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
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "BUNDLE_WEATHER_EXTRA"
        fun newInstance(weather: Weather): WeatherDetailFragment {

            //На вебинаре использовали вместо run apply можно ли использовать run? (работает)
            WeatherDetailFragment().run {
                arguments = Bundle().apply { putParcelable(BUNDLE_WEATHER_EXTRA, weather) }
                return this
            }
        }
    }
}