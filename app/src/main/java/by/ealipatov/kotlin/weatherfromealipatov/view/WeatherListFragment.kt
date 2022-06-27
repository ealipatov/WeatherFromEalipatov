package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppState
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
    get() {
        return _binding!!
    }
    lateinit var viewModel: WeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { //binding не бывает null, удалили знак вопроса View?
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получим viewModel из "списка" моделей по шаблону WeatherListViewModel::class.java
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        //Подпишемся на liveData
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })
        viewModel.sendRequest()
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE

                Snackbar
                    .make(
                        requireView(),
                        appState.error.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.sendRequest() }
                    .show()
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val result = appState.weatherData
                binding.cityName.text = result.city.name
                binding.temperatureValue.text = result.temperature.toString()
                binding.feelsLikeValue.text = result.feelsLike.toString()
                val coordinates = "${result.city.lat} / ${result.city.lon}"
                binding.cityCoordinates.text = coordinates
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return WeatherListFragment()
        }
        // Сокращенная запись кода функции сверху
        // fun newInstance() = WeatherListFragment()
    }

}