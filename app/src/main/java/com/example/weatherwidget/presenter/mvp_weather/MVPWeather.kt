package com.example.weatherwidget.presenter.mvp_weather

import com.example.weatherwidget.model.remote.data_weather.WeatherResponse

interface MVPWeather {
    interface WeatherPresenter{
        fun getWeatherData(city: String)
    }
    interface WeatherView{
        fun setResult(weatherResponse: WeatherResponse)
        fun onLoad(isLoading: Boolean)
        fun showError(message: String)
    }
}