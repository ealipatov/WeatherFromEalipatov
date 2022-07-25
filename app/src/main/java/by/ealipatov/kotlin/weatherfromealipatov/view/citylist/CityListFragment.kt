package by.ealipatov.kotlin.weatherfromealipatov.view.citylist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.CountryName
import by.ealipatov.kotlin.weatherfromealipatov.utils.REQUEST_CODE_LOCATION
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
import java.util.*
import kotlin.system.measureTimeMillis

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

    private fun getAddress(location: Location) {
        val geocoder = Geocoder(context, Locale("ru_RU"))
        measureTimeMillis {
            Thread{
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                onItemClick(Weather(City(address.first().countryName, address.first().locality, location.latitude, location.longitude)))
            }.start()
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
                if (location != null) {
                    getAddress(location)
                }

            }
        }
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

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun selectCountry(cont: Int) {
        when (countries[cont]) {
            "Мир" -> {
                viewModel.getWeatherListForLocation(CountryName.World)
            }
            "Беларусь" -> {
                viewModel.getWeatherListForLocation(CountryName.Belarus)
            }
            "Россия" -> {
                viewModel.getWeatherListForLocation(CountryName.Russian)
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
                    viewModel.getWeatherListForLocation(CountryName.World)
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