package by.ealipatov.kotlin.weatherfromealipatov.model

sealed class Location{
    object Russian:Location()
    object World:Location()
    object Belarus:Location()
}