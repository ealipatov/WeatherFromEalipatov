package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.Location
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppState
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_list.*


class WeatherListFragment : Fragment(), OnItemClick {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }
    lateinit var viewModel: WeatherListViewModel

    val countries = arrayOf("Мир", "Беларусь", "Россия")

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

        //Реализуем выбор страны отображения списка городов через всплывающий список spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, countries
            )
            binding.spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    when (countries[position]) {
                        "Мир" -> {
                            viewModel.getWeatherListFor(Location.World)
                        }
                        "Беларусь" -> {
                            viewModel.getWeatherListFor(Location.Belarus)
                        }
                        "Россия" -> {
                            viewModel.getWeatherListFor(Location.Russian)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
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
                    .setAction(getString(R.string.reload)) { viewModel.getWeatherListFor(Location.World) }
                    .show()
            }
            //Показ прогрессбара во время загрузки
            is AppState.Loading -> {
                binding.weatherListLoadingLayout.visibility = View.VISIBLE
            }

            //Отображение погоды в одном городе
            is AppState.Success -> {
                binding.weatherListLoadingLayout.visibility = View.GONE
             //   val result = appState.weatherData
            }
            //Отображение погоды в списке городов
            is AppState.SuccessList -> {
                binding.weatherListLoadingLayout.visibility = View.GONE

                binding.weatherListRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetailFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }

    //Обнуление binding для предотвращения утечки памяти
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = WeatherListFragment()
    }


}