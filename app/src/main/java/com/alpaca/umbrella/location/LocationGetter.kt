package com.alpaca.umbrella.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import android.Manifest.permission.ACCESS_FINE_LOCATION;
import android.app.Activity
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationGetter constructor(activity: Activity) {

    private lateinit var client: FusedLocationProviderClient
    private var activity: Activity = activity

    fun init() {
        requestPermission()

        this.client = LocationServices.getFusedLocationProviderClient(activity)


    }

    fun getLocation() {

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("ERROR", "NO PERMISSIONS")
            return
        }

        this.client.lastLocation.addOnFailureListener {
            Log.d("ERROR", "PICAT " + it.stackTrace)
        }

        this.client.lastLocation.addOnSuccessListener {
            Log.d("MERE", it.latitude.toString() + " " + it.longitude.toString())
        }

    }


    private fun requestPermission() {
        Log.d("MERE", "PERMISIUNI")
        ActivityCompat.requestPermissions(activity, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 1);
    }
}