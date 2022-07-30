package by.ealipatov.kotlin.weatherfromealipatov

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import by.ealipatov.kotlin.weatherfromealipatov.databinding.ActivityMainBinding
import by.ealipatov.kotlin.weatherfromealipatov.utils.*
import by.ealipatov.kotlin.weatherfromealipatov.view.citylist.CityListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CityListFragment.newInstance()).commit()
        }

        @Suppress("DEPRECATION")
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStateReceiver, filter)

        pushNotification("Погода сегодня", "Какие-то данные о погоде")
    }

    private var networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (!noConnectivity) {
                Toast.makeText(context, "Connection found", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Connection lost", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun pushNotification(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, CHANNEL_WEATHER_ID)
        notification.apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(R.drawable.ic_baseline_beach_access_24)
            priority = NotificationCompat.PRIORITY_MAX
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelWeather = NotificationChannel(
                CHANNEL_WEATHER_ID,
                CHANNEL_WEATHER_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channelWeather.description = "Канал уведомлений о погоде"
            notificationManager.createNotificationChannel(channelWeather)
        }
        notificationManager.notify(NOTIFICATION_WEATHER_ID, notification.build())
    }
}