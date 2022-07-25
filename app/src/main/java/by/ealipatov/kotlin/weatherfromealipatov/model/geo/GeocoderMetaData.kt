package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeocoderMetaData(
    val Address: Address,
    val AddressDetails: AddressDetails,
    val kind: String,
    val precision: String,
    val text: String
): Parcelable