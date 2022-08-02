package by.ealipatov.kotlin.weatherfromealipatov.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City,
    var temperature: Int = 0,
    var feelsLike: Int = 0,
    var condition: String = "",
    var icon: String = "ovc_-ra"
) : Parcelable

@Parcelize
data class City(
    var country: String,
    var name: String,
    var lat: Double,
    var lon: Double
) : Parcelable

fun getBelarusianCities(): List<Weather> {
    return listOf(
        Weather(City("Беларусь", "Минск", 53.9, 27.5667), 27, 35),
        Weather(City("Беларусь", "Брест", 52.0975, 23.6877), 33, 35),
        Weather(City("Беларусь", "Витебск", 55.1904, 30.2049), 25, 29),
        Weather(City("Беларусь", "Гомель", 52.4345, 30.9754), 35, 35),
        Weather(City("Беларусь", "Гродно", 53.6884, 23.8258), 28, 30),
        Weather(City("Беларусь", "Лида", 53.8833, 25.2997), 27, 29),
        Weather(City("Беларусь", "Могилёв", 53.9168, 30.3449), 28, 28)
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Россия", "Москва", 55.755826, 37.617299900000035), 1, 2),
        Weather(City("Россия", "Санкт-Петербург", 59.9342802, 30.335098600000038), 3, 3),
        Weather(City("Россия", "Новосибирск", 55.00835259999999, 82.93573270000002), 5, 6),
        Weather(City("Россия", "Екатеринбург", 56.83892609999999, 60.60570250000001), 7, 8),
        Weather(City("Россия", "Нижний Новгород", 56.2965039, 43.936059), 9, 10),
        Weather(City("Россия", "Казань", 55.8304307, 49.06608060000008), 11, 12),
        Weather(City("Россия", "Челябинск", 55.1644419, 61.4368432), 13, 14),
        Weather(City("Россия", "Омск", 54.9884804, 73.32423610000001), 15, 16),
        Weather(City("Россия", "Ростов-на-Дону", 47.2357137, 39.701505), 17, 18),
        Weather(City("Россия", "Уфа", 54.7387621, 55.972055400000045), 19, 20)
    )
}

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Мир", "Лондон", 51.5085300, -0.1257400), 1, 2),
        Weather(City("Мир", "Токио", 35.6895000, 139.6917100), 3, 4),
        Weather(City("Мир", "Париж", 48.8534100, 2.3488000), 5, 6),
        Weather(City("Мир", "Берлин", 52.52000659999999, 13.404953999999975), 7, 8),
        Weather(City("Мир", "Рим", 41.9027835, 12.496365500000024), 9, 10),
        Weather(City("Мир", "Минск", 53.90453979999999, 27.561524400000053), 11, 12),
        Weather(City("Мир", "Стамбул", 41.0082376, 28.97835889999999), 13, 14),
        Weather(City("Мир", "Вашингтон", 38.9071923, -77.03687070000001), 15, 16),
        Weather(City("Мир", "Киев", 50.4501, 30.523400000000038), 17, 18),
        Weather(City("Мир", "Пекин", 39.90419989999999, 116.40739630000007), 19, 20)
    )
}

//fun getDefaultCity() = City("Россия", "Москва", 55.755826, 37.617299900000035)