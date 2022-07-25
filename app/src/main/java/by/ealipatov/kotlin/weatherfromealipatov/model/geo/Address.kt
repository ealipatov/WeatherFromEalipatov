package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val Components: List<Component>,
    val country_code: String,
    val formatted: String
)