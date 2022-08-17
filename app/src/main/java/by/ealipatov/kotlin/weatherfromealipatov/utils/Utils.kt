package by.ealipatov.kotlin.weatherfromealipatov.utils

import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.domain.Weather
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.Fact
import by.ealipatov.kotlin.weatherfromealipatov.model.dto.WeatherDTO

fun converterWeatherDTOWithCityToWeather(weatherDTO: WeatherDTO, city: City): Weather {
    val fact: Fact = weatherDTO.fact
    return (Weather(city, fact.temp, fact.feels_like,fact.condition,fact.icon))
}

fun translateWeatherCondition(word: String): String {
    return when (word) {
        "clear" -> "Ясно"
        "partly-cloudy" -> "Малооблачно"
        "cloudy" -> "Облачно с прояснениями"
        "overcast" -> "Пасмурно"
        "drizzle" -> "Морось"
        "light-rain" -> "Небольшой дождь"
        "rain" -> "Дождь"
        "moderate-rain" -> "Умеренно сильный дождь"
        "heavy-rain" -> "Сильный дождь"
        "continuous_heavy_rain" -> "Длительный сильный дождь"
        "showers" -> "Ливень"
        "wet-snow" -> "Дождь со снегом"
        "light-snow" -> "Небольшой снег"
        "snow" -> "Снег"
        "snow-showers" -> "Снегопад"
        "hail" -> "Град"
        "thunderstorm" -> "Гроза"
        "thunderstorm-with-rain" -> "Дождь с грозой"
        "thunderstorm-with-hail" -> "Гроза с градом"
        else -> {
            word
        }
    }
}

