package by.ealipatov.kotlin.weatherfromealipatov.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListItemViewBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view){
    fun bind(weather: Weather){
        val binding = FragmentWeatherListItemViewBinding.bind(itemView)
        binding.cityName.text = weather.city.name
    }

}