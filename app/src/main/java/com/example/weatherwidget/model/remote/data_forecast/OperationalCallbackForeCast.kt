package com.example.weatherwidget.model.remote.data_forecast

interface OperationalCallbackForeCast {
    fun onSuccess(forecastResponse: ForecastResponse)
    fun onFailure(message: String)
}