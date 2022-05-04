package org.maksym.openweather.data.repository

import org.maksym.openweather.data.remote.WeatherService

class WeatherRepository constructor(private val weatherService: WeatherService) {

    fun getForecasts() = weatherService.getWeatherForCoords(WeatherService.appId, 48.97378, 14.4761205, 10)
}