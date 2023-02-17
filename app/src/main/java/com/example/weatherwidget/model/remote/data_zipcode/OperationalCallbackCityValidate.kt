package com.example.weatherwidget.model.remote.data_zipcode

interface OperationalCallbackCityValidate {
    fun onSuccess(cityValidateResponse: CityValidateResponse)
    fun onFailure(message: String)
}