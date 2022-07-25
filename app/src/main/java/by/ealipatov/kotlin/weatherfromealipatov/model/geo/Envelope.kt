package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Envelope(
    val lowerCorner: String,
    val upperCorner: String
)