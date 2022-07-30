package by.ealipatov.kotlin.weatherfromealipatov.utils

import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.Fact
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO

fun converterWeatherDTOWithCityToWeather(weatherDTO: WeatherDTO, city: City): Weather {
    val fact: Fact = weatherDTO.fact
    return (Weather(city, fact.temp, fact.feels_like,fact.condition,fact.icon))
}

fun convertWeatherToWeatherDTO(weather: Weather): WeatherDTO {
    val fact = Fact(weather.condition, weather.feelsLike, weather.temperature,weather.icon)
    return WeatherDTO(fact)
}
