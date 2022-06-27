package by.ealipatov.kotlin.weatherfromealipatov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.ealipatov.kotlin.weatherfromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.weatherfromealipatov.view.WeatherListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}