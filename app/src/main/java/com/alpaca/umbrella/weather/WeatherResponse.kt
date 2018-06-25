package com.alpaca.umbrella.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse (@SerializedName("city") var city : City,
                            @SerializedName("list") var forecast : List<ForecastDetail>?)


data class City(@SerializedName("name") var cityName : String,
                @SerializedName("country") var country : String)

data class ForecastDetail(@SerializedName("dt") var  date: Long,
                          @SerializedName("main") var main : Main,
                          @SerializedName("weather") var weather : List<WeatherDescription>,
                          @SerializedName("clouds") var clouds : Clouds,
                          @SerializedName("wind") var wind : Wind,
                          @SerializedName("dt_txt") var  dateText: String)

data class Main (@SerializedName("temp") var temp: Double,
                 @SerializedName("temp_min") var temp_min: Double,
                 @SerializedName("temp_max") var temp_max: Double,
                 @SerializedName("pressure") var pressure: Double,
                 @SerializedName("humidity") var humidity: Double)

/*
 *  WeatherResponse -> List<ForecastDetail> -> List<WeatherDescription> -> main
 *  Possible values: [Rain, Snow, Extreme, Clouds, others?]
 */
data class WeatherDescription(@SerializedName("main") var main : String,
                              @SerializedName("description") var description: String,
                              @SerializedName("icon") var icon: String)

data class Clouds (@SerializedName("all") var all: Double)

data class Wind (@SerializedName("speed") var speed: Double,
                 @SerializedName("deg") var deg: Double)