package com.example.weatherwidget.model.remote.data_weather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)