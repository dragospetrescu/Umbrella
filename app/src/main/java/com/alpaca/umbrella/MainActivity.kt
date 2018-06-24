package com.alpaca.umbrella

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    fun getWeatherFromAPI(cityName : String) {

        val call: Call<WeatherResponse> = api.getForecast(cityName, 1)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val res: WeatherResponse? = response.body()

                Log.d("RESPONSE", response.toString())
                Log.d("RESPONSE BODY", response.body().toString())

                Toast.makeText(applicationContext,"Temp is " + res?.forecast!![0].main.temp,
                        Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

}


