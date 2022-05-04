package org.maksym.openweather.data.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Temperature {
    @SerializedName("day")
    internal var dayTemp: Double? = null
}

class Forecast {
    @SerializedName("dt")
    private val datetime: Long? = null
        get() = field?.times(1000)

    @SerializedName("temp")
    private val temp: Temperature? = null

    fun asWeekDay(): String {
        return SimpleDateFormat("EEE", Locale("cs", "CZ")).format(datetime).capitalize(Locale.ROOT)
    }

    fun asShortDate(): String {
        return SimpleDateFormat("d.M", Locale.getDefault()).format(datetime)
    }

    fun tempInCelsius(): String {
        return (temp?.dayTemp?.minus(273.15F)?.roundToInt().toString()) + "°C"
    }
}

class ForecastResponse {

    @SerializedName("list")
    val list: List<Forecast>? = null
}