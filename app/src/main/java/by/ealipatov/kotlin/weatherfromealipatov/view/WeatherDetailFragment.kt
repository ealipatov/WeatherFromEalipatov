package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherDetailBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppStateDetailViewModel
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherDetailViewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

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

        weather = arguments?.getParcelable(BUNDLE_WEATHER_EXTRA)!! //костыль наверное

        //Урок 7.
        weather.let { localWeather ->
            //Проверка наличия интернета
            if(viewModel.isConnection(requireContext())){
                viewModel.getWeather(localWeather.city.lat, localWeather.city.lon)
                viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
                    renderData(appState)
                }
            }
        }
    }

    //Урок 7.
    private fun renderData(appState: AppStateDetailViewModel) {

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
                    weatherIcon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact.icon}.svg")

                    //icon.load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg"){
                    /*icon.load(R.drawable.loadingfast){
                        error(R.drawable.ic_earth)
                        placeholder(R.drawable.loadingfast)
                        transformations(CircleCropTransformation())
                    }*/
                }
            }
        }
    }

    private fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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
        const val BUNDLE_WEATHER_EXTRA = "BUNDLE_WEATHER_EXTRA"

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