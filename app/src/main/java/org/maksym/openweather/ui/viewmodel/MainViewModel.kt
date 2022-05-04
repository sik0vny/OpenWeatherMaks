package org.maksym.openweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.maksym.openweather.data.model.Forecast
import org.maksym.openweather.data.model.ForecastResponse
import org.maksym.openweather.data.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: WeatherRepository)  : ViewModel() {

    val forecastsList = MutableLiveData<List<Forecast>>()
    val errorMessage = MutableLiveData<String>()

    fun getForecasts() {
        val response = repository.getForecasts()
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