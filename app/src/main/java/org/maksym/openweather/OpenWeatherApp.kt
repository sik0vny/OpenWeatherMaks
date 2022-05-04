package org.maksym.openweather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.maksym.openweather.di.ViewModelModule

class OpenWeatherMaks : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OpenWeatherMaks)
            modules(listOf(ViewModelModule.module))
        }
    }
}