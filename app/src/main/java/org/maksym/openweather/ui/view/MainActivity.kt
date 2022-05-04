package org.maksym.openweather.ui.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.maksym.openweather.data.remote.WeatherService
import org.maksym.openweather.data.repository.WeatherRepository
import org.maksym.openweather.databinding.ActivityMainBinding
import org.maksym.openweather.ui.viewmodel.MainViewModel
import org.maksym.openweather.ui.viewmodel.ViewModelFactory
import android.widget.ArrayAdapter
import org.maksym.openweather.ui.viewmodel.PLACES


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val weatherService = WeatherService.getInstance()
    private var adapter = ForecastsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.maksym.openweather.R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(WeatherRepository(weatherService))).get(
            MainViewModel::class.java).apply {

            forecastsList.observe(this@MainActivity, {
                adapter.setForecastsList(it)
            })

            errorMessage.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            })
        }

        binding.recyclerItemsView.adapter = adapter

        binding.placeSpinner.let {
            val spinnerAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                PLACES.map { place -> place.key }
            )

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            it.adapter = spinnerAdapter

            it.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    val placeName: String = parent?.getItemAtPosition(position) as String
                    viewModel.getForecasts(placeName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}