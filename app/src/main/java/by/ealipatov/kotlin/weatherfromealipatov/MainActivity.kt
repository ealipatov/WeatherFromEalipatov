package by.ealipatov.kotlin.weatherfromealipatov

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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

        pushNotification(
            "Погода сегодня",
            "Какие-то данные о погоде",
            "Канал новостей о погоде",
            CHANNEL_WEATHER_ID,
            CHANNEL_WEATHER_NAME,
            NOTIFICATION_WEATHER_ID
        )
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

    private fun pushNotification(
        message_title: String,
        message_body: String,
        channel_description: String,
        channel_id: String,
        channel_name: String,
        notification_id: Int
    ) {

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, channel_id)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notification.apply {
            setContentTitle(message_title)
            setContentText(message_body)
            setSmallIcon(R.drawable.ic_baseline_beach_access_24)
            priority = NotificationCompat.PRIORITY_MAX
            setContentIntent(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelWeather = NotificationChannel(
                channel_id,
                channel_name,
                NotificationManager.IMPORTANCE_HIGH
            )
            channelWeather.description = channel_description
            notificationManager.createNotificationChannel(channelWeather)
        }
        notificationManager.notify(notification_id, notification.build())
    }
}