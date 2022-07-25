package by.ealipatov.kotlin.weatherfromealipatov.view.weatherhistorylist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherHistoryListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryWeatherAll
import by.ealipatov.kotlin.weatherfromealipatov.view.AboutFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.SearchFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.WeatherDetailFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.citylist.CityListFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.citylist.OnItemClick
import by.ealipatov.kotlin.weatherfromealipatov.view.contactlist.ContactListFragment
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist.AppStateHistoryWeatherListViewModel
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.weatherhistorylist.WeatherHistoryListViewModel


class WeatherHistoryListFragment : Fragment(), OnItemClick {

    private var _binding: FragmentWeatherHistoryListBinding? = null
    private val binding: FragmentWeatherHistoryListBinding
        get() {
            return _binding!!
        }

    lateinit var viewModel: WeatherHistoryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather_history_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by -> {
                sortList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


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

    private fun sortList(){
      // Toast.makeText(requireContext(),"Тут будет сортировка списка", Toast.LENGTH_LONG).show()
       viewModel.getSortedAllHistory()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
