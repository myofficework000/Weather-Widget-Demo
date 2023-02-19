package com.example.weatherwidget.presenter.mvp_weather

import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_weather.OperationalCallBackWeather
import com.example.weatherwidget.model.remote.data_weather.WeatherResponse

class WeatherPresenter(
    private val volleyHandler: VolleyHandler,
    private val weatherView: MVPWeather.WeatherView)
    :MVPWeather.WeatherPresenter{
    override fun getWeatherData(city: String) {
        weatherView.onLoad(true)
        volleyHandler.getWeatherData(city, object: OperationalCallBackWeather{
            override fun onSuccess(weatherResponse: WeatherResponse) {
                weatherView.onLoad(false)
                weatherView.setResult(weatherResponse)
            }

            override fun onFailure(message: String) {
                weatherView.onLoad(false)
                weatherView.showError(message)
            }
        })
    }

}