package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding


class WeatherListFragment : Fragment() {

    lateinit var binding : FragmentWeatherListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { //binding не бывает null, удалили знак вопроса View?
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    companion object{
        fun newInstance() : Fragment{
            return WeatherListFragment()
        }
        // Сокращенная запись кода функции сверху
        // fun newInstance() = WeatherListFragment()
    }

}