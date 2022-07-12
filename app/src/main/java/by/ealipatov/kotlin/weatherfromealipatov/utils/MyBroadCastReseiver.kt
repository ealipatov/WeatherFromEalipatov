package by.ealipatov.kotlin.weatherfromealipatov.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadCastReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("***"," MyBroadCastReceiver ${intent!!.action}")
            Toast.makeText(context," MyBroadCastReceiver ${intent.action}",Toast.LENGTH_LONG).show()
        }
}