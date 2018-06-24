package com.alpaca.umbrella

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alpaca.umbrella.weather.OpenWeatherAPI
import com.alpaca.umbrella.weather.OpenWeatherAPIModule
import com.alpaca.umbrella.weather.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

import com.alpaca.umbrella.weather.OpenWeatherAPIComponent.DaggerOpenWeatherAPIComponent

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit  var api : OpenWeatherAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDI()
        getForecastForSevenDays("bucharest")


    }

    private fun injectDI() {
        DaggerOpenWeatherAPIComponent
                .builder()
                .openWeatherAPIModule(OpenWeatherAPIModule())
                .build()
                .inject()
    }

    fun getForecastForSevenDays(cityName: String) {
        api.dailyForecast(cityName, 7).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    //This method will be created in the next step.
                    //createListForView(it)

                    toast("Salut!")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}


