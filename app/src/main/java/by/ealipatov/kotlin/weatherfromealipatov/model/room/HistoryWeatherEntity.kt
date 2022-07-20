package by.ealipatov.kotlin.weatherfromealipatov.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_weather_entity_table")
data class HistoryWeatherEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lon")
    val lon: Double,
    @ColumnInfo(name = "temperature")
    var temperature: Int,
    @ColumnInfo(name = "feelsLike")
    var feelsLike: Int,
    @ColumnInfo(name = "condition")
    var condition: String,
    @ColumnInfo(name = "icon")
    var icon: String
)