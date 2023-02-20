package com.example.weatherwidget.model.remote

import android.graphics.drawable.GradientDrawable

/*
This class is responsible to add constant of project
*/
object Constant {
    const val ABSOLUTE_ZERO = 273.15
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "979f405df6be739658dd4ebbde6eb52c"
    const val END_POINT_WEATHER = "data/2.5/weather?q=Houston&appid=$API_KEY"
    const val END_POINT_FORECAST = "data/2.5/forecast"
    const val END_POINT_AIR_POLLUTION = "data/2.5/air_pollution/forecast"
    const val END_POINT_ZIPCODE_SAMPLE = "geo/1.0/zip?zip=81658&appid=$API_KEY"
    const val END_POINT_ZIPCODE = "geo/1.0/zip"
    const val END_POINT_CITY_VALIDATE = "data/2.5/weather"

    // first: Latitude, second: Longitude
    // Also this is Long Beach of Los Angeles.
    val PLACEHOLDER_COORDS = Pair(33.7701, 118.1937)

    val AIRPOLLUTIONBARGRADIENT = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            0xff60fe00.toInt(),
            0xffb9ff00.toInt(),
            0xfffeff80.toInt(),
            0xfffca754.toInt(),
            0xfffe0707.toInt()
        )
    )

    const val IMG_URL="https://openweathermap.org/img/wn"

    val COUNTRY_CODE = arrayOf("US", "IN", "GB", "RU")

    const val DATABASE_NAME = "weather-db"
    const val DATABASE_VERSION = 1
    const val CITY_TABLE_NAME = "tbCity"
    const val ID_COL = "id"
    const val ZIP_COL = "zip"
    const val CITY_COL = "city"
    const val LAT_COL = "lat"
    const val LON_COL = "lon"
    const val COUNTRY_COL = "country"

    const val SHARED_PREF_FILE = "WeatherAppPreference"
    const val SHARED_PREF_CITY_KEY = "city-name"
    const val SHARED_PREF_CITY_LAT = "city-lat"
    const val SHARED_PREF_CITY_LON = "city-lon"
    const val SHARED_PREF_CITY_SUNSET = "sunset"
    const val SHARED_PREF_CITY_TIME_NOW = "time-now"


    enum class AirPollutionDataType {
        CAQI,
        CO,
        NO,
        NO2,
        O3,
        SO2,
        PM2_5,
        PM10,
        NH3
    }

    enum class Days {
        MON,
        TUE,
        WED,
        THURS,
        FRI,
        SAT,
        SUN
    }
}