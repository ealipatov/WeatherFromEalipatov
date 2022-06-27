package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWeatherListBinding
import by.ealipatov.kotlin.weatherfromealipatov.viewmodel.WeatherListViewModel


class WeatherListFragment : Fragment() {

    lateinit var binding : FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { //binding не бывает null, удалили знак вопроса View?
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //Получим viewModel из "списка" моделей по шаблону WeatherListViewModel::class.java
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
    //Подпишемся на liveData
        viewModel.liveData.observe(viewLifecycleOwner, object : Observer<Any>{
            override fun onChanged(t: Any?) {
               Toast.makeText(requireContext(), "tested", Toast.LENGTH_LONG).show()
            }

        })

    }

    companion object{
        fun newInstance() : Fragment{
            return WeatherListFragment()
        }
        // Сокращенная запись кода функции сверху
        // fun newInstance() = WeatherListFragment()
    }

}