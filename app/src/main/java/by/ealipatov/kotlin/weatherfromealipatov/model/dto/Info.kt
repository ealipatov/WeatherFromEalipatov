package by.ealipatov.kotlin.weatherfromealipatov.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Info(
    val lat: Double,
    val lon: Double,
    val url: String
): Parcelable