package org.maksym.openweather.ui.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.maksym.openweather.databinding.ActivityMainBinding
import org.maksym.openweather.ui.viewmodel.MainViewModel
import android.widget.ArrayAdapter
import org.maksym.openweather.ui.viewmodel.PLACES
import android.text.Editable

import android.text.TextWatcher
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.maksym.openweather.R


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var adapter = ForecastsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apply {

            forecastsList.observe(this@MainActivity, {
                adapter.setForecastsList(it)
            })

            errorMessage.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            })

            loaded.observe(this@MainActivity, {
                binding.progressDialog.visibility = if (it) View.GONE else View.VISIBLE
            })
        }

        binding.run {

            fun requestForecasts() {
                val placeName: String = binding.placeSpinner.getItemAtPosition(binding.placeSpinner.selectedItemPosition) as String
                val count = Integer.parseInt(binding.daysEdit.text.toString())
                viewModel.getForecasts(placeName, count)
            }

            fun validateInputs(executeIfValid: () -> Unit) {
                val limit = 17 // this is an API limit
                if (daysEdit.text.isEmpty() ||
                    daysEdit.text.toString() == "-" ||
                    daysEdit.text.toString().toInt() !in (1..limit)
                ) {
                    daysEdit.error = getString(R.string.validation_msg)
                    recyclerItemsView.visibility = View.GONE
                } else {
                    daysEdit.error = null
                    recyclerItemsView.visibility = View.VISIBLE
                    executeIfValid.invoke()
                }
            }

            recyclerItemsView.adapter = adapter

            placeSpinner.let {
                val spinnerAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    PLACES.map { place -> place.key }
                )

                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                it.adapter = spinnerAdapter

                it.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View, position: Int, id: Long
                    ) {
                        validateInputs { requestForecasts() }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            daysEdit.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

                override fun afterTextChanged(s: Editable) {
                    validateInputs { requestForecasts() }
                }
            })

        }

    }

}