package com.alpaca.umbrella.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.util.Log
import com.alpaca.umbrella.weather.WeatherManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationManager constructor(private var activity: Activity, private var weatherManager: WeatherManager) {

    private lateinit var client: FusedLocationProviderClient

    fun init() {
        requestPermission()

        this.client = LocationServices.getFusedLocationProviderClient(activity)

    }

    fun getLocation() {

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("ERROR", "NO PERMISSIONS")
            requestPermission()
        }

        this.client.lastLocation.addOnFailureListener {
            Log.d("ERROR", it.stackTrace.toString())
        }

        this.client.lastLocation.addOnSuccessListener {

            Log.d("DEBUG", "Got location: " + it.latitude.toString() + " " + it.longitude.toString())
            weatherManager.getWeatherFromAPI(null, it.latitude, it.longitude)
        }
    }


    private fun requestPermission() {
        Log.d("DEBUG", "Asking for permissions")
        ActivityCompat.requestPermissions(activity, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 1)

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        }
    }
}