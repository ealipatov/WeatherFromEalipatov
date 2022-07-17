package by.ealipatov.kotlin.weatherfromealipatov.view

import android.content.Context
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
import by.ealipatov.kotlin.weatherfromealipatov.utils.SPINNER_SHARED_PREFERENCE_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.SPINNER_SHARED_PREFERENCE_NAME
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.AppStateListViewModel
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

    val countries = arrayOf("Выберете страну:","Мир", "Беларусь", "Россия")//Костыль*

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        val spSpinner = requireActivity().getSharedPreferences(SPINNER_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)

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
                    if(position!=0){ //Костыль*
                        spSpinner.edit().apply(){
                            putInt(SPINNER_SHARED_PREFERENCE_KEY,position)
                            apply()
                        }
                    }
                    selectCountry(spSpinner.getInt(SPINNER_SHARED_PREFERENCE_KEY,0))
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun selectCountry(cont: Int){
        when (countries[cont]) {
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

    private fun renderData(appState: AppStateListViewModel) {
        when (appState) {
            is AppStateListViewModel.Error -> {
                binding.showResult()

                binding.root.snakeBarErr(
                    appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE,
                getString(R.string.reload)) {
                    viewModel.getWeatherListForLocation(Location.World)
                }
            }

            is AppStateListViewModel.Loading -> {
                binding.loading()
            }

            is AppStateListViewModel.Success -> {
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
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = WeatherListFragment()
    }

}