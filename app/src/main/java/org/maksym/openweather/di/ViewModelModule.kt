package org.maksym.openweather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.maksym.openweather.data.remote.WeatherService
import org.maksym.openweather.data.repository.WeatherRepository
import org.maksym.openweather.ui.viewmodel.MainViewModel

object ViewModelModule {

    val module = module {

        single { WeatherRepository(WeatherService.getInstance()) }

        viewModel {
            MainViewModel(get())
        }
    }
}