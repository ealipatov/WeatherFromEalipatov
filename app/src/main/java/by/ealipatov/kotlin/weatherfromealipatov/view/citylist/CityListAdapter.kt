package by.ealipatov.kotlin.weatherfromealipatov.view.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListItemViewBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather


class CityListAdapter(private val dataList: List<Weather>, private val callback: OnItemClick) :
    RecyclerView.Adapter<CityListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            FragmentWeatherListItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class WeatherViewHolder(private val binding:FragmentWeatherListItemViewBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(weather: Weather) {
            binding.cityName.text = weather.city.name
            binding.temperatureValue.text = weather.temperature.toString()
            binding.root.setOnClickListener {
                callback.onItemClick(weather)
            }
        }
    }
}
