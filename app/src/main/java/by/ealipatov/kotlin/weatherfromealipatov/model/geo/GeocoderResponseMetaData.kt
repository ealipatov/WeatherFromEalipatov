package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeocoderResponseMetaData(
    val found: String,
    val request: String,
    val results: String
): Parcelable