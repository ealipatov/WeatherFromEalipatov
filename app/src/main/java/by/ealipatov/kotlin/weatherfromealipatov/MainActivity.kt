package by.ealipatov.kotlin.weatherfromealipatov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.ealipatov.kotlin.weatherfromealipatov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textOnScreen.text = getString(R.string.test_text)
    }
}