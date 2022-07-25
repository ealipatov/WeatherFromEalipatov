package by.ealipatov.kotlin.weatherfromealipatov.view.citylist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.Location
import by.ealipatov.kotlin.weatherfromealipatov.utils.SPINNER_SHARED_PREFERENCE_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.SPINNER_SHARED_PREFERENCE_NAME
import by.ealipatov.kotlin.weatherfromealipatov.view.AboutFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.SearchFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.WeatherDetailFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.contactlist.ContactListFragment
import by.ealipatov.kotlin.weatherfromealipatov.view.weatherhistorylist.WeatherHistoryListFragment
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.citieslist.AppStateCitiesListViewModel
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.citieslist.CitiesListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_list.*

class CityListFragment : Fragment(), OnItemClick {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    lateinit var viewModel: CitiesListViewModel

    private val countries = arrayOf("Выберете страну:", "Мир", "Беларусь", "Россия")//Костыль*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_city_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                if (requireActivity().supportFragmentManager.findFragmentByTag("about")==null){
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, AboutFragment(), "about")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.search -> {
                if (requireActivity().supportFragmentManager.findFragmentByTag("search")==null){
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, SearchFragment(),"search")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.history -> {
                if (requireActivity().supportFragmentManager.findFragmentByTag("weatherHistory")==null) {
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, WeatherHistoryListFragment(), "weatherHistory")
                            .addToBackStack("")
                            .commit()
                    }
                }
                true
            }
            R.id.menu_content_provider -> {
                if (requireActivity().supportFragmentManager.findFragmentByTag("contactList")==null){
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, ContactListFragment(), "contactList")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }

                }
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
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        val spSpinner = requireActivity().getSharedPreferences(
            SPINNER_SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

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
                    if (position != 0) { //Костыль*
                        spSpinner.edit().apply {
                            putInt(SPINNER_SHARED_PREFERENCE_KEY, position)
                            apply()
                        }
                    }
                    selectCountry(spSpinner.getInt(SPINNER_SHARED_PREFERENCE_KEY, 0))
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
        binding.myLocationFAB.setOnClickListener {
           checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val criteria = Criteria()
                val provider = locationManager.getBestProvider(criteria,true)
                val location = provider?.let {
                    locationManager.getLastKnownLocation(it)
                }
                Log.d("***", location.toString())

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L,
                        100F
                    ) { }
            }
        }
    }

    private val REQUEST_CODE_LOCATION = 999

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    private fun checkPermission(permission: String) {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        PackageManager.PERMISSION_GRANTED
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("Объяснение Объяснение Объяснение Объяснение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }

    }

    private fun selectCountry(cont: Int) {
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

    private fun renderData(appState: AppStateCitiesListViewModel) {
        when (appState) {
            is AppStateCitiesListViewModel.Error -> {
                binding.showResult()

                binding.root.snakeBarErr(
                    appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.reload)
                ) {
                    viewModel.getWeatherListForLocation(Location.World)
                }
            }

            is AppStateCitiesListViewModel.Loading -> {
                binding.loading()
            }

            is AppStateCitiesListViewModel.Success -> {
                binding.showResult()
                binding.weatherListRecyclerView.adapter =
                    CityListAdapter(appState.weatherList, this)
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

    private fun View.snakeBarErr(
        string: String,
        duration: Int,
        actionText: String,
        block: (v: View) -> Unit
    ) {
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
        fun newInstance() = CityListFragment()
    }
}