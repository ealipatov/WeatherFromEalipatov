package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MetaDataPropertyX(
    val GeocoderResponseMetaData: GeocoderResponseMetaData
): Parcelable