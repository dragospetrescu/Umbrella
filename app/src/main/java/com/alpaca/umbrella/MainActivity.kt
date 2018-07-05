package com.alpaca.umbrella

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alpaca.umbrella.weather.*
import com.alpaca.umbrella.location.LocationManager

class MainActivity: AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var weatherManager: WeatherManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        weatherManager = WeatherManager(this, applicationContext)
        weatherManager.init()
        locationManager = LocationManager(this, weatherManager)
        locationManager.init()
    }

    /* on button click */
    fun getWeather(v: View) {
        locationManager.getLocation()
    }
}


