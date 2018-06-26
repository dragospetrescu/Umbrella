package com.alpaca.umbrella

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alpaca.umbrella.weather.*
import com.alpaca.umbrella.location.LocationManager
import com.alpaca.umbrella.weather.OpenWeatherAPI
import com.alpaca.umbrella.weather.OpenWeatherInterceptor
import com.alpaca.umbrella.weather.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity: AppCompatActivity() {

    private lateinit var api : OpenWeatherAPI
    private lateinit var retrofit: Retrofit
    private var messageFactory: MessageFactory = MessageFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val locationManager = LocationManager(this)
        locationManager.init()
        locationManager.getLocation()


        val apiClient = OkHttpClient.Builder()
                            .addInterceptor(OpenWeatherInterceptor()).build()

        retrofit = Retrofit.Builder().apply {
            baseUrl(OpenWeatherAPI.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(apiClient)
        }.build()

        api = retrofit.create(OpenWeatherAPI::class.java)
    }

    /* on button click */
    fun getWeather(v: View) {
        getWeatherFromAPI("bucharest")
    }

    private fun getWeatherFromAPI(cityName : String? = null, latitude : Double? = null,
                                  longitude: Double? = null) {

        val call: Call<WeatherResponse>

        if (cityName != null)
            call = api.getForecast(cityName, 1)
        else if (latitude != null && longitude != null)
            call = api.getForecast(latitude, longitude, 1)
        else {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            return
        }

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val res: WeatherResponse? = response.body()

                Log.d("RESPONSE", response.toString())
                Log.d("RESPONSE BODY", response.body()?.toString())

                if (response.isSuccessful) {
                    Log.d("TEMPERATURE", res?.forecast!![0].main.temp.toString())
                    Toast.makeText(applicationContext, messageFactory.get(res), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Unsuccessful call to weather API", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}


