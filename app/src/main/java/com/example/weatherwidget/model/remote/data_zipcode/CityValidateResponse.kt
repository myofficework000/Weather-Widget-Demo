package com.example.weatherwidget.model.remote.data_zipcode

data class CityValidateResponse(
    val base: String,
    val clouds: Clouds,
    val cod: String,
    val coord: Coord,
    val dt: String,
    val id: String,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: String,
    val visibility: String,
    val weather: ArrayList<Weather>,
    val wind: Wind,
    val message: String
)