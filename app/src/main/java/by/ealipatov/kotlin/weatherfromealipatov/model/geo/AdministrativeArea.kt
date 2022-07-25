package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdministrativeArea(
    val AdministrativeAreaName: String,
    val Locality: Locality
)