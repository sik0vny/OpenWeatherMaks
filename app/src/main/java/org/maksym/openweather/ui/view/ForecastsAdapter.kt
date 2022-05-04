package org.maksym.openweather.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.maksym.openweather.data.model.Forecast
import org.maksym.openweather.databinding.ForecastItemBinding

class ForecastsAdapter: RecyclerView.Adapter<ForecastsViewHolder>() {

    private var forecasts = mutableListOf<Forecast>()

    fun setForecastsList(forecasts: List<Forecast>) {
        this.forecasts = forecasts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemBinding.inflate(inflater, parent, false)
        return ForecastsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastsViewHolder, position: Int) {
        val forecast = forecasts[position]

        holder.binding.run {
            weekDay.text = forecast.asWeekDay()
            shortDate.text = forecast.asShortDate()
            temperature.text = forecast.tempInCelsius()
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }
}


class ForecastsViewHolder(val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) { }