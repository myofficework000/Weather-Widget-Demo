package com.example.weatherwidget.presenter.mvp_forecast

import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse

interface MVPForecast {
    interface ForecastPresenter {
        fun getForecast(city: String)
    }

    interface ForecastView {
        fun onLoad(isLoading: Boolean)
        fun showError(message: String)
        fun setResult(forecastResponse: ForecastResponse)
    }
}