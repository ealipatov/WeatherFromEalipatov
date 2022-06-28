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
import by.ealipatov.kotlin.weatherfromealipatov.model.Location
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

    var isRussian = true

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

        //viewModel.getWeatherListFor(Location.Belarus)

        binding.weatherListFragmentFAB.setOnClickListener{
            isRussian = !isRussian
            if(isRussian){
                viewModel.getWeatherListFor(Location.Russian)
            } else {
                viewModel.getWeatherListFor(Location.World)
            }
        }

        viewModel.getWeatherListFor(Location.World)
    }

    fun renderData(appState: AppState) {
        when (appState) {
            //Обработка ошибки (исключения)
            is AppState.Error -> {
                binding.weatherListLoadingLayout.visibility = View.GONE

                Snackbar
                    .make(
                        requireView(),
                        appState.error.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getWeatherListFor(Location.Belarus) }
                    .show()
            }
            //Показ прогрессбара во время загрузки
            is AppState.Loading -> {
                binding.weatherListLoadingLayout.visibility = View.VISIBLE
            }

            //Отображение погоды в одном городе
            is AppState.Success -> {
                binding.weatherListLoadingLayout.visibility = View.GONE
                val result = appState.weatherData

//                binding.cityName.text = result.city.name
//                binding.temperatureValue.text = result.temperature.toString()
//                binding.feelsLikeValue.text = result.feelsLike.toString()
//                val coordinates = "${result.city.lat} / ${result.city.lon}"
//                binding.cityCoordinates.text = coordinates
            }
            //Отображение погоды в списке городов
            is AppState.SuccessList ->{
                binding.weatherListLoadingLayout.visibility = View.GONE
                val result = appState.weatherList

                binding.weatherListRecyclerView.adapter = WeatherListAdapter(appState.weatherList)

            }
        }
    }

    //Обнуление binding для предотвращения утечки памяти
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