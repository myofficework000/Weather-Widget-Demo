package com.example.weatherwidget.model.remote

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

/*
This class is responsible to add constant of project
*/
object Constant {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "979f405df6be739658dd4ebbde6eb52c"
    const val END_POINT_WEATHER = "data/2.5/weather?q=Houston&appid=$API_KEY"
    const val END_POINT_FORECAST = "data/2.5/forecast?q=Chicago&appid=$API_KEY"
    const val END_POINT_AIR_POLLUTION = "data/2.5/air_pollution/forecast"
    const val END_POINT_ZIPCODE = "geo/1.0/zip?zip=81658&appid=$API_KEY"

    // first: Latitude, second: Longitude
    // Also this is Long Beach of Los Angeles.
    val PLACEHOLDER_COORDS = Pair(33.7701, 118.1937)

    val AIRPOLLUTIONBARGRADIENT = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            0xff9cff9c.toInt(),
            0xff31ff00.toInt(),
            0xff31cf00.toInt(),
            0xffffff00.toInt(),
            0xffffcf00.toInt(),
            0xffff9a00.toInt(),
            0xffff6464.toInt(),
            0xffff0000.toInt(),
            0xff990000.toInt(),
            0xffce30ff.toInt()
        )
    )
}