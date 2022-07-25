package by.ealipatov.kotlin.weatherfromealipatov.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom (historyWeatherEntity: HistoryWeatherEntity)

    @Query("SELECT * FROM history_weather_entity_table WHERE lat= :sLat AND lon= :sLon") //* - прочитать все поля, можно отдельное поле
    fun getHistoryWeatherByCoordinates(sLat: Double, sLon: Double): List<HistoryWeatherEntity>

    //Получить все записи (без условий отбора)
    @Query("SELECT * FROM history_weather_entity_table")
    fun getHistoryWeatherAll(): List<HistoryWeatherEntity>
}