package by.ealipatov.kotlin.weatherfromealipatov.model.geo


data class Address(
    val Components: List<Component>,
    val country_code: String,
    val formatted: String
)