package org.maksym.openweather.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.maksym.openweather.BuildConfig
import org.maksym.openweather.data.model.ForecastResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/forecast/daily")
    fun getWeatherForCoords(
        @Query("appid") appId: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") count: Int
    ): Call<ForecastResponse>

    companion object {

        private const val baseUrl = "https://api.openweathermap.org"
        const val appId = "<put your api key here>"

        private var weatherService: WeatherService? = null

        private val client = OkHttpClient().newBuilder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()


        fun getInstance() : WeatherService {
            if (weatherService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                weatherService = retrofit.create(WeatherService::class.java)
            }
            return weatherService!!
        }
    }
}