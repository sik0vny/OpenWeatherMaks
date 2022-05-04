package org.maksym.openweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.maksym.openweather.data.model.Forecast
import org.maksym.openweather.data.model.ForecastResponse
import org.maksym.openweather.data.model.LatLon
import org.maksym.openweather.data.repository.WeatherRepository
import retrofit2.Response


val PLACES = hashMapOf(
    "České Budějovice"  to LatLon(48.97378881915517, 14.476120512906416),
    "New York"          to LatLon(40.79052384606425, -73.95908688800822),
    "Sydney"            to LatLon(33.8470241774331, 151.0624326592654)
)

class MainViewModel constructor(private val repository: WeatherRepository) : ViewModel() {
    val forecastsList = MutableLiveData<List<Forecast>>()
    val errorMessage = MutableLiveData<String>()

    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError("Exception  ${throwable.localizedMessage}")
    }

    val loaded = MutableLiveData(false)

    fun getForecasts(placeName: String, count: Int) {
        val latLon = PLACES[placeName]!!

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loaded.postValue(false)
            val response: Response<ForecastResponse> = repository.getForecasts(latLon, count)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    forecastsList.postValue(response.body()?.list)
                    loaded.value = true
                } else {
                    handleError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun handleError(message: String) {
        errorMessage.postValue(message)
        loaded.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}