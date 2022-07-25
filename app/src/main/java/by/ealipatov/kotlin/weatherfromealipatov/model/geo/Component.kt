package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Component(
    val kind: String,
    val name: String
)