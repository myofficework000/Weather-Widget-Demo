package com.example.weatherwidget.presenter.mvp_air_pollution

import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse

interface MVPAirPollution {

    interface ICallback{
        fun onSuccess(data: AirPollutionResponse?)
        fun onError(message: String?)
    }
    interface IPresenter{
        fun getAirPollutionData(latitude: Double, longitude: Double)
        fun getAirPollutionSummaryText(daqi: Int): String
    }
    interface IView{
        fun onSuccess(data: AirPollutionResponse?)
        fun onError(message: String?)
    }
}