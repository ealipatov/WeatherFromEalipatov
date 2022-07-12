package by.ealipatov.kotlin.weatherfromealipatov.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Part(
    val condition: String,
    val daytime: String,
    val feels_like: Int,
    val humidity: Int,
    val icon: String,
    val part_name: String,
    val polar: Boolean,
    val prec_mm: Double,
    val prec_period: Double,
    val prec_prob: Double,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val temp_avg: Int,
    val temp_max: Int,
    val temp_min: Int,
    val temp_water: Int,
    val wind_dir: String,
    val wind_gust: Double,
    val wind_speed: Double
): Parcelable