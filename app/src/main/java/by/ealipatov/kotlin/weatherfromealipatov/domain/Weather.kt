package by.ealipatov.kotlin.weatherfromealipatov.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 20,
    val feelsLike: Int = 25
)

data class City(
    val name: String,
    val lat: Double,
    val lon: Double
)
fun getBelarusianCities(): List<Weather> {
    return listOf(
        Weather(City("Минск", 53.9,27.5667),27, 35),
        Weather(City("Брест", 52.0975,23.6877),33, 35),
        Weather(City("Витебска", 55.1904, 30.2049), 25, 29),
        Weather(City("Гомеля", 52.4345, 30.9754), 35,35),
        Weather(City("Гродно", 53.6884, 23.8258), 28, 30),
        Weather(City("Лида", 53.8833, 25.2997), 27, 29),
        Weather(City("Могилёв", 53.9168, 30.3449), 28, 28)
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Москва", 55.755826, 37.617299900000035), 1, 2),
        Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), 3, 3),
        Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), 5, 6),
        Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001), 7, 8),
        Weather(City("Нижний Новгород", 56.2965039, 43.936059), 9, 10),
        Weather(City("Казань", 55.8304307, 49.06608060000008), 11, 12),
        Weather(City("Челябинск", 55.1644419, 61.4368432), 13, 14),
        Weather(City("Омск", 54.9884804, 73.32423610000001), 15, 16),
        Weather(City("Ростов-на-Дону", 47.2357137, 39.701505), 17, 18),
        Weather(City("Уфа", 54.7387621, 55.972055400000045), 19, 20)
    )
}

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Лондон", 51.5085300, -0.1257400), 1, 2),
        Weather(City("Токио", 35.6895000, 139.6917100), 3, 4),
        Weather(City("Париж", 48.8534100, 2.3488000), 5, 6),
        Weather(City("Берлин", 52.52000659999999, 13.404953999999975), 7, 8),
        Weather(City("Рим", 41.9027835, 12.496365500000024), 9, 10),
        Weather(City("Минск", 53.90453979999999, 27.561524400000053), 11, 12),
        Weather(City("Стамбул", 41.0082376, 28.97835889999999), 13, 14),
        Weather(City("Вашингтон", 38.9071923, -77.03687070000001), 15, 16),
        Weather(City("Киев", 50.4501, 30.523400000000038), 17, 18),
        Weather(City("Пекин", 39.90419989999999, 116.40739630000007), 19, 20)
    )
}

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)