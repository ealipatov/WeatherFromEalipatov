package by.ealipatov.kotlin.weatherfromealipatov.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentSearchBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.CallbackCityCoordinates
import by.ealipatov.kotlin.weatherfromealipatov.model.RepositoryCityCoordinatesByCityNameLoader
import by.ealipatov.kotlin.weatherfromealipatov.utils.CITY_SHARED_PREFERENCE_KEY
import by.ealipatov.kotlin.weatherfromealipatov.utils.CITY_SHARED_PREFERENCE_NAME
import java.io.IOException

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() {
            return _binding!!
        }

    private lateinit var weatherSearchCity: Weather
    private val repository = RepositoryCityCoordinatesByCityNameLoader()

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

        val spCityName = requireActivity().getSharedPreferences(
            CITY_SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        //Урок 8 Почему-то не отображает введенный ранее город. Разобраться позже.
        binding.cityName.text = Editable.Factory.getInstance()
            .newEditable(spCityName.getString(CITY_SHARED_PREFERENCE_KEY, ""))

        val callback = object : CallbackCityCoordinates {
            override fun onResponse(city: City) {
                weatherSearchCity = Weather(city, 0, 0)
                onSearchClick(weatherSearchCity)
            }

            override fun onFailure(e: IOException) {
                //обработать ошибку
            }
        }

        binding.searchSendBtn.setOnClickListener() {
            repository.getCityCoordinates(binding.cityName.text.toString(), callback)

        }

        spCityName.edit().apply() {
            putString(CITY_SHARED_PREFERENCE_KEY, binding.cityName.text.toString())
            apply()
        }
    }

    private fun onSearchClick(weather: Weather) {
        parentFragmentManager.beginTransaction().hide(this).add(
            R.id.container, WeatherDetailFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}