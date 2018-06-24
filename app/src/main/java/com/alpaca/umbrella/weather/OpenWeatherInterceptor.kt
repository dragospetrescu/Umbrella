package com.alpaca.umbrella.weather

import com.alpaca.umbrella.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class OpenWeatherInterceptor : Interceptor {

    val OPEN_WEATHER_API_KEY="4d3d290cf9e12a02ef48c1cfa976f26f"

    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url().newBuilder().addQueryParameter("APPID", OPEN_WEATHER_API_KEY).
                addQueryParameter("mode", "json").
                addQueryParameter("units", "metric").build()
        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
    }

}