package org.maksym.openweather.data.repository

import org.maksym.openweather.data.model.LatLon
import org.maksym.openweather.data.remote.WeatherService

class WeatherRepository constructor(private val weatherService: WeatherService) {
    suspend fun getForecasts(latLon: LatLon, count: Int) = weatherService.getWeatherForCoords(WeatherService.appId, latLon.lat,  latLon.lon, count)
}