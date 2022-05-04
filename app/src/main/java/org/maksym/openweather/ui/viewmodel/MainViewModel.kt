package org.maksym.openweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.maksym.openweather.data.model.Forecast
import org.maksym.openweather.data.model.ForecastResponse
import org.maksym.openweather.data.model.LatLon
import org.maksym.openweather.data.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


val PLACES = hashMapOf(
    "České Budějovice" to LatLon(48.97378881915517, 14.476120512906416),
    "New York" to LatLon(40.79052384606425, -73.95908688800822),
    "Sydney" to LatLon(33.8470241774331, 151.0624326592654)
)

class MainViewModel constructor(private val repository: WeatherRepository)  : ViewModel() {
    val forecastsList = MutableLiveData<List<Forecast>>()
    val errorMessage = MutableLiveData<String>()

    fun getForecasts(placeName: String, count: Int) {
        val latLon = PLACES[placeName]!!
        val response = repository.getForecasts(latLon, count)

        response.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                forecastsList.postValue(response.body()?.list)
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}