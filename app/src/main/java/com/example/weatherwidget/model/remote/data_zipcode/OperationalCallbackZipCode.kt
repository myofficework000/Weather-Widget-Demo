package com.example.weatherwidget.model.remote.data_zipcode

interface OperationalCallbackZipCode {
    fun onSuccess(zipcodeResponse: ZipcodeResponse)
    fun onFailure(message: String)
}