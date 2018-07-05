package com.alpaca.umbrella.weather

import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.alpaca.umbrella.R
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Utility class for getting an appropriate message
 * according to a given weather description
 */
class MessageFactory(private var activity: Activity) {

    companion object {
        const val SNOW_MESSAGE = "It’s snowing! Grab your gloves and go make a snowman!"
        const val RAIN_MESSAGE = "It’s raining! Don’t forget your umbrella-ella-ella-eh."

        val MESSAGES = arrayOf(
                "It’s literally freezing out there! Don’t forget to take your scarf and beanie.",
                "If I were you, I would put a warm jacket on.",
                "If I were you, I would put a light jacket on.",
                "Summer days are on,T-shirt and shorts FTW!",
                "Put your sunglasses on!",
                "I would not go out if I were you. But if you insist, don’t forget sunscreen!")

        val BOUNDS = doubleArrayOf(0.0, 15.0, 22.0, 30.0, 40.0)
    }

    fun get(weather: WeatherResponse?): String {

        val weatherDescription: String? = weather?.forecast!![0].weather[0].main
        val temperature: Double = weather.forecast!![0].main.temp

        if(temperature <= 0.0) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_running_shoe)
            activity.pantsImageView.setImageResource(R.drawable.pants_jeans)
            activity.bodyImageView.setImageResource(R.drawable.shirt_jacket)
            activity.hatImageView.setImageResource(R.drawable.head_winter_hat)
        } else if(temperature <= 15.0) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_running_shoe)
            activity.pantsImageView.setImageResource(R.drawable.pants_jeans)
            activity.bodyImageView.setImageResource(R.drawable.shirt_hoodie)
        } else if(temperature <= 22.0) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_running_shoe)
            activity.pantsImageView.setImageResource(R.drawable.pants_jeans)
            activity.bodyImageView.setImageResource(R.drawable.shirt_blouse)
        } else if(temperature <= 30.0) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_running_shoe)
            activity.pantsImageView.setImageResource(R.drawable.pants_shorts)
            activity.bodyImageView.setImageResource(R.drawable.shirt_tshirt)
        } else if(temperature <= 40.0) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_flip_flops)
            activity.pantsImageView.setImageResource(R.drawable.pants_shorts)
            activity.bodyImageView.setImageResource(R.drawable.shirt_tshirt)
            activity.hatImageView.setImageResource(R.drawable.head_baseball_cap)
        }

        if (weatherDescription.equals("snow", true)) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_boot)
            activity.leftHandImageView.setImageResource(R.drawable.hand_glove)
            activity.rightHandImageView.setImageResource(R.drawable.hand_glove)
        }

        if (weatherDescription.equals("rain", true)) {
            activity.shoesImageView.setImageResource(R.drawable.shoes_boot)
            activity.leftHandImageView.setImageResource(R.drawable.hand_umbrella)
        }


        if (weatherDescription != null) {
            if (weatherDescription.equals("rain", true)) {
                return RAIN_MESSAGE
            }
            if (weatherDescription.equals("snow", true)) {
                return SNOW_MESSAGE
            }
        }

        return MESSAGES[BOUNDS.indexOfFirst { x -> temperature <= x }]
    }
}