package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
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
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        //Реализуем выбор страны отображения списка городов через всплывающий список spinner
        spinner?.let {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, countries
            )

            binding.spinner.adapter = adapter
            it.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    when (countries[position]) {
                        "Мир" -> {
                            viewModel.getWeatherListForLocation(Location.World)
                        }
                        "Беларусь" -> {
                            viewModel.getWeatherListForLocation(Location.Belarus)
                        }
                        "Россия" -> {
                            viewModel.getWeatherListForLocation(Location.Russian)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            //Обработка ошибки (исключения)
            is AppState.Error -> {
                binding.showResult()

                //Вариант вызова снекбара из ДЗ (код из вэбинара)
                binding.root.snakeBarErr(
                    appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE,
                getString(R.string.reload)) {
                    viewModel.getWeatherListForLocation(Location.World)
                }
            }

            //Показ прогрессбара во время загрузки
            is AppState.Loading -> {
                binding.loading()
            }

            //Отображение погоды в одном городе
            is AppState.Success -> {
                binding.showResult()

            }
            //Отображение погоды в списке городов
            is AppState.SuccessList -> {
                binding.showResult()

                binding.weatherListRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)
            }
        }
    }

    private fun FragmentWeatherListBinding.loading() {
        this.weatherListLoadingLayout.visibility = View.GONE
        this.weatherListLoadingLayout.visibility = View.VISIBLE
    }

    private fun FragmentWeatherListBinding.showResult() {
        this.weatherListLoadingLayout.visibility = View.VISIBLE
        this.weatherListLoadingLayout.visibility = View.GONE
    }


    private fun View.snakeBarErr(string: String, duration: Int, actionText:String, block: (v: View) -> Unit) {
        Snackbar.make(this, string, duration).setAction(actionText, block).show()
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