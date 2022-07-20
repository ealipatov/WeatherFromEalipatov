package by.ealipatov.kotlin.weatherfromealipatov.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val date: String,
    val date_ts: Int,
    val moon_code: Int,
    val moon_text: String,
    val parts: List<Part>,
    val sunrise: String,
    val sunset: String,
    val week: Int
): Parcelable