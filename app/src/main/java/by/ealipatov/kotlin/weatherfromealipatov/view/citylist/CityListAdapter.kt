package by.ealipatov.kotlin.weatherfromealipatov.view.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListItemViewBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather


class CityListAdapter(private val dataList: List<Weather>, private val callback: OnItemClick) :
    RecyclerView.Adapter<CityListAdapter.WeatherViewHolder>() {

    //Создание контейнера (3.40 lesson)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        //"Надули" макет через байдинг
        val binding =
            FragmentWeatherListItemViewBinding.inflate(LayoutInflater.from(parent.context))
        //Передали в корневой макет
       return WeatherViewHolder(binding)
    }

    //Заполнение контейнера
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        //Передаем погоду
        holder.bind(dataList[position])
    }

    //Количество элементов
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
