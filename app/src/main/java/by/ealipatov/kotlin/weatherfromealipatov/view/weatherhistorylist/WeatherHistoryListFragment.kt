package by.ealipatov.kotlin.weatherfromealipatov.view.weatherhistorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherHistoryListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.view.WeatherDetailFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.citylist.OnItemClick
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist.AppStateHistoryWeatherListViewModel
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist.WeatherHistoryListViewModel

class WeatherHistoryListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherHistoryListFragment()
    }

    private var _binding: FragmentWeatherHistoryListBinding? = null
    private val binding: FragmentWeatherHistoryListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: WeatherHistoryListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherHistoryListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherHistoryListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getAllHistory()
    }

    private fun renderData(appStateHistoryWeatherListViewModel: AppStateHistoryWeatherListViewModel) {
        when (appStateHistoryWeatherListViewModel) {
            is AppStateHistoryWeatherListViewModel.Error -> {
            }
            AppStateHistoryWeatherListViewModel.Loading -> {
            }
            is AppStateHistoryWeatherListViewModel.Success -> {
                binding.historyFragmentRecyclerView.adapter =
                    WeatherHistoryListAdapter(appStateHistoryWeatherListViewModel.weatherList, this)
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetailFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}
