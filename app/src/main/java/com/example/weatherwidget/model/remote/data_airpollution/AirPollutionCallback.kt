package com.example.weatherwidget.model.remote.data_airpollution

interface AirPollutionCallback {
    fun onLoad()
    fun onSuccess(response: AirPollutionResponse)
    fun onFailure(message: String)
}