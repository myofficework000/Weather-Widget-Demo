package com.example.weatherwidget.model.remote

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
}