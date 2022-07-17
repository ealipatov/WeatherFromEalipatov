package by.ealipatov.kotlin.weatherfromealipatov.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_weather_entity_table")
data class HistoryWeatherEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    var temperature: Int,
    var feelsLike: Int,
    var condition: String,
    var icon: String
)