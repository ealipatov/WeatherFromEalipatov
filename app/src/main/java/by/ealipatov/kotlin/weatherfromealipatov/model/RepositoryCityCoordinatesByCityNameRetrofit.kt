package by.ealipatov.kotlin.weatherfromealipatov.model

import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.domain.City
import by.ealipatov.kotlin.weatherfromealipatov.model.geo.CityCoordinates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface CityCoordinatesAPI {
    @GET("/1.x/")
    fun getCityName(
        @Query("apikey") keyValue: String,
        @Query("geocode") cityName: String,
        @Query("format") format: String = "json",
        @Query("results") results: String = "1"

    ): Call<CityCoordinates>
}

class RepositoryCityCoordinatesByCityNameRetrofit : RepositoryCityCoordinatesByCityName {
    override fun getCityCoordinates(cityName: String, callback: CallbackCityCoordinates) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://geocode-maps.yandex.ru")
        retrofitImpl.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        val api = retrofitImpl.build().create(CityCoordinatesAPI::class.java)
        api.getCityName(BuildConfig.GEOCODER_API_KEY, cityName).enqueue(object :
            Callback<CityCoordinates> {
            override fun onResponse(
                call: Call<CityCoordinates>,
                response: Response<CityCoordinates>
            ) {
                if (response.isSuccessful && response.body() != null) {
                   if (response.body()!!.response.GeoObjectCollection.featureMember.isNotEmpty()) {
                       val parts =
                           response.body()!!.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(
                               " "
                           )
                       val searchCity = City(
                           response.body()!!.response.GeoObjectCollection.featureMember[0]
                               .GeoObject.metaDataProperty.GeocoderMetaData.AddressDetails.Country.CountryName,
                           response.body()!!.response.GeoObjectCollection.featureMember[0].GeoObject.name,
                           parts[1].toDouble(),
                           parts[0].toDouble()
                       )
                       callback.onResponse(searchCity)
                   } else {
                       callback.onFailure(IOException("Не корректный запрос, введите правильное название города"))
                   }
                }
                else {
                    callback.onFailure(IOException("Не удалось выполнить запрос"))
                }
            }

            override fun onFailure(call: Call<CityCoordinates>, t: Throwable) {
                callback.onFailure(t as IOException) //костыль
            }
        })
    }
}
