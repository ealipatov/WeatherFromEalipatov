package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val AddressLine: String,
    val AdministrativeArea: AdministrativeArea,
    val CountryName: String,
    val CountryNameCode: String
)