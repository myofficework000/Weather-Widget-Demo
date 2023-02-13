package com.example.weatherwidget.model.remote.data_weather

import com.example.weatherwidget.model.remote.data_zipcode.ZipcodeResponse

interface OperationalCallBackWeather {
    fun onSuccess(weatherResponse: WeatherResponse)
    fun onFailure(message: String)
}