package by.ealipatov.kotlin.weatherfromealipatov

import android.app.Application
import androidx.room.Room
import by.ealipatov.kotlin.weatherfromealipatov.model.room.HistoryWeatherDatabase
import by.ealipatov.kotlin.weatherfromealipatov.utils.ROOM_DB_NAME

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        private var historyWeatherDatabase: HistoryWeatherDatabase? = null
        fun getMyApp() = myApp!!

        fun getHistoryWeatherDatabase(): HistoryWeatherDatabase {
            if (historyWeatherDatabase == null) {
                historyWeatherDatabase = Room.databaseBuilder(
                    getMyApp(),
                    HistoryWeatherDatabase::class.java,
                    ROOM_DB_NAME
                ).build()
            }
            return historyWeatherDatabase!!
        }
    }
}