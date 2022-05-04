package org.maksym.openweather.data.repository

import org.maksym.openweather.data.model.ForecastResponse
import org.maksym.openweather.data.model.LatLon
import org.maksym.openweather.data.remote.WeatherService
import retrofit2.Call

class WeatherRepository constructor(private val weatherService: WeatherService) {

    fun getForecasts(latLon: LatLon): Call<ForecastResponse> {

        return weatherService.getWeatherForCoords(WeatherService.appId, latLon.lat,  latLon.lon, 10)
    }
}