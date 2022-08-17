package by.ealipatov.kotlin.weatherfromealipatov.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fact(
    val condition: String,
//    val daytime: String,
    val feels_like: Int,
    val temp: Int,
//    val humidity: Int,
    val icon: String = "bkn_n"
//    val obs_time: Int,
//    val polar: Boolean,val temp: Int
//    val pressure_mm: Int,
//    val pressure_pa: Int,
//    val season: String,

//    val temp_water: Int,
//    val wind_dir: String,
//    val wind_gust: Double,
//    val wind_speed: Double
): Parcelable