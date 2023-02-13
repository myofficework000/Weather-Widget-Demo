package com.example.weatherwidget.presenter.mvp_forecast

interface MVPForecast {
    interface ForecastPresenter {
        fun getForecast()
    }

    interface ForecastView {
        fun onLoad(isLoading: Boolean)
        fun showError(message: String)
    }
}