package com.example.weatherwidget.presenter.mvp_air_pollution

import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.model.remote.data_airpollution.PollutionData

interface MVPAirPollution {

    interface ICallback{
        fun onSuccess(data: AirPollutionResponse?)
        fun onError(message: String?)
    }
    interface IPresenter{
        fun getAirPollutionData(latitude: Double, longitude: Double)
        fun getAirPollutionSummaryText(level: Int): String
        fun getAirPollutionDataFraction(
            dataType: Constant.AirPollutionDataType,
            value: PollutionData?): Double
    }
    interface IView{
        fun onSuccess(data: AirPollutionResponse?)
        fun onError(message: String?)
    }
}