package com.alpaca.umbrella.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

    @GET("forecast")
    fun getForecast(@Query("q") cityName : String,
                    @Query("cnt") dayCount : Int) : Call<WeatherResponse>

    @GET("forecast")
    fun getForecast(@Query("lat") latitude : Double,
                    @Query("lon") longitude : Double,
                    @Query("cnt") dayCount : Int) : Call<WeatherResponse>
}