package by.ealipatov.kotlin.weatherfromealipatov.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryWeatherEntity::class], version = 1)
abstract class HistoryWeatherDatabase: RoomDatabase() {
    abstract fun historyWeatherDAO(): HistoryWeatherDAO
}