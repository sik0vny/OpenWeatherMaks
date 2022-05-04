package org.maksym.openweather.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.maksym.openweather.R
import org.maksym.openweather.data.remote.WeatherService
import org.maksym.openweather.data.repository.WeatherRepository
import org.maksym.openweather.databinding.ActivityMainBinding
import org.maksym.openweather.ui.viewmodel.MainViewModel
import org.maksym.openweather.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val weatherService = WeatherService.getInstance()
    private val adapter = ForecastsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(WeatherRepository(weatherService))).get(
            MainViewModel::class.java)

        binding.recyclerItemsView.adapter = adapter

        viewModel.forecastsList.observe(this, {
            adapter.setForecastsList(it)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        })
        viewModel.getForecasts()
    }
}