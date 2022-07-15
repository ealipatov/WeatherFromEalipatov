package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.utils.BUNDLE_WEATHER_EXTRA
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppStateDetailViewModel
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherDetailViewModel
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding: FragmentWeatherDetailBinding
        get() {
            return _binding!!
        }

    lateinit var weather: Weather
    private val viewModel by lazy {
        ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
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

        //Урок 7.
        weather = arguments?.getParcelable(BUNDLE_WEATHER_EXTRA)!! //костыль наверное

        //Урок 7.
        weather.let { localWeather ->
            //Проверка наличия интернета
            if (viewModel.isConnection(requireContext())) {
                viewModel.getWeather(localWeather.city.lat, localWeather.city.lon)
                viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
                    renderData(appState)
                }
            } else Toast.makeText(requireContext(), "Нет подключения интернета", Toast.LENGTH_LONG)
                .show()
        }
    }

    //Урок 7.
    private fun renderData(appState: AppStateDetailViewModel) {

        //Подсказка от преподавателя для отображения "картинок"
        val imageLoader = ImageLoader.Builder(requireContext())
            .components {
                //GIF
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                //SVG
                add(SvgDecoder.Factory())
            }
            .build()

        when (appState) {
            is AppStateDetailViewModel.Error -> {}
            AppStateDetailViewModel.Loading -> {}
            is AppStateDetailViewModel.Success -> {
                with(binding) {
                    val coordinates = fun(lat: Double, lon: Double) = "$lat / $lon"
                    val weatherDTO = appState.weatherData
                    cityName.text = weather.city.name
                    temperatureValue.text = weatherDTO.fact.temp.toString()
                    feelsLikeValue.text = weatherDTO.fact.feels_like.toString()
                    cityCoordinates.text = coordinates(weather.city.lat, weather.city.lat)
                    weatherCondition.text = translate(weatherDTO.fact.condition)

                    Coil.setImageLoader(imageLoader)
                    weatherIcon.load("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact.icon}.svg")
                    {
                        error(R.drawable.ic_baseline_no_photography_24)
                        placeholder(R.drawable.loadingfast)
                        //transformations(CircleCropTransformation())
                    }

                }
            }
        }
    }

    private fun translate(f: String): String {
        return when (f) {
            "clear" -> "Ясно"
            "partly-cloudy" -> "Малооблачно"
            "cloudy" -> "Облачно с прояснениями"
            "overcast" -> "Пасмурно"
            "drizzle" -> "Морось"
            "light-rain" -> "Небольшой дождь"
            "rain" -> "Дождь"
            "moderate-rain" -> "Умеренно сильный дождь"
            "heavy-rain" -> "Сильный дождь"
            "continuous_heavy_rain" -> "Длительный сильный дождь"
            "showers" -> "Ливень"
            "wet-snow" -> "Дождь со снегом"
            "light-snow" -> "Небольшой снег"
            "snow" -> "Снег"
            "snow-showers" -> "Снегопад"
            "hail" -> "Град"
            "thunderstorm" -> "Гроза"
            "thunderstorm-with-rain" -> "Дождь с грозой"
            "thunderstorm-with-hail" -> "Гроза с градом"
            else -> {
                f
            }
        }
    }

    companion object {
        //Исправление кода согласно замечанию преподавателя
        fun newInstance(weather: Weather) = WeatherDetailFragment().also {
            it.arguments = Bundle().apply { putParcelable(BUNDLE_WEATHER_EXTRA, weather) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}