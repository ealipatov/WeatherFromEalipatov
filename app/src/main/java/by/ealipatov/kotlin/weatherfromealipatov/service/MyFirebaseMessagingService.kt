package by.ealipatov.kotlin.weatherfromealipatov.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import by.ealipatov.kotlin.weatherfromealipatov.MainActivity
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.utils.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("***", "key token: $token")

        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("@@@", "$message ")
        val data = message.data
        val title = data[NOTIFICATION_KEY_TITLE]
        val body = data[NOTIFICATION_KEY_BODY]
        if (!title.isNullOrEmpty() && !body.isNullOrEmpty()) {
            pushNotification(title, body)
        }
        super.onMessageReceived(message)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun pushNotification(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            this, 1,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_WEATHER_ID).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.ic_baseline_beach_access_24)
            priority = NotificationCompat.PRIORITY_MAX
            setContentIntent(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelHigh = NotificationChannel(
                CHANNEL_WEATHER_ID, CHANNEL_WEATHER_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channelHigh.description = "Канал новостей о погоде"
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_WEATHER_ID, notification.build())

    }
}