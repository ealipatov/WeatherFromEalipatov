package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentSearchBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.CallbackCityCoordinates
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryCityCoordinatesByCityNameRetrofit
import java.io.IOException

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() {
            return _binding!!
        }

    private val repository = RepositoryCityCoordinatesByCityNameRetrofit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : CallbackCityCoordinates {
            override fun onResponse(city: City) {
                val weatherSearchCity = Weather(city, 0, 0)
                onSearchClick(weatherSearchCity)
            }

            override fun onFailure(e: IOException) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
            }
        }

        binding.searchSendBtn.setOnClickListener {
            repository.getCityCoordinates(binding.cityName.text.toString(), callback)
        }
    }

    private fun onSearchClick(weather: Weather) {
        childFragmentManager.beginTransaction().hide(SearchFragment()).add(
            R.id.searchContainer, WeatherDetailFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}