package by.ealipatov.kotlin.weatherfromealipatov.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListItemViewBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather

class WeatherListAdapter(private val dataList: List<Weather>, private val callback: OnItemClick) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    //Создание контейнера (3.40 lesson)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        //"Надули" макет через байдинг
        val binding =
            FragmentWeatherListItemViewBinding.inflate(LayoutInflater.from(parent.context))
        //Передали в корневой макет
        return WeatherViewHolder(binding.root)
    }

    //Заполнение контейнера
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        //Передаем погоду
        holder.bind(dataList[position])
    }

    //Количество элементов
    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather) {
            val binding = FragmentWeatherListItemViewBinding.bind(itemView)
            binding.cityName.text = weather.city.name
            binding.root.setOnClickListener {
                callback.onItemClick(weather)
            }
        }
    }
}
