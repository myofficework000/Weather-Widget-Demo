package com.example.weatherwidget.model.remote.data_zipcode

data class ZipcodeResponse(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val zip: String
)