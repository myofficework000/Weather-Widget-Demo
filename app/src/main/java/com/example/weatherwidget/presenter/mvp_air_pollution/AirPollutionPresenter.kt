package com.example.weatherwidget.presenter.mvp_air_pollution

import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.model.remote.data_airpollution.PollutionData
import kotlin.math.floor

class AirPollutionPresenter(
    private val mvpView: MVPAirPollution.IView,
    private val handler: VolleyHandler
): MVPAirPollution.IPresenter {
    override fun getAirPollutionData(
        latitude: Double, longitude: Double
    ) {
        handler.getAirPollutionData(latitude, longitude, object: MVPAirPollution.ICallback {
            override fun onSuccess(data: AirPollutionResponse?) { mvpView.onSuccess(data) }
            override fun onError(message: String?) { mvpView.onError(message) }
        })
    }

    override fun getAirPollutionSummaryText(level: Int): String = "$level - ${
        when(level) {
            1 -> "Good"
            2 -> "Fair"
            3 -> "Moderate"
            4 -> "Poor"
            5 -> "Very Poor"
            else -> "N/A"
        }
    }"

    override fun getAirPollutionDataFraction(
        dataType: Constant.AirPollutionDataType,
        value: PollutionData?
    ): Double = value?.run {
        when (dataType) {
            Constant.AirPollutionDataType.CAQI -> main.aqi.toDouble() / 5
            Constant.AirPollutionDataType.NO2 -> when (floor(components.no2).toInt()) {
                in 0..50 -> 0.2
                in 50..100 -> 0.4
                in 100..200 -> 0.6
                in 200..400 -> 0.8
                in 400..Int.MAX_VALUE -> 1.0
                else -> 0.0
            }
            Constant.AirPollutionDataType.O3 -> when (floor(components.o3).toInt()) {
                in 0..60 -> 0.2
                in 60..120 -> 0.4
                in 120..180 -> 0.6
                in 180..240 -> 0.8
                in 240..Int.MAX_VALUE -> 1.0
                else -> 0.0
            }
            Constant.AirPollutionDataType.PM2_5 -> when (floor(components.pm2_5).toInt()) {
                in 0..15 -> 0.2
                in 15..30 -> 0.4
                in 30..55 -> 0.6
                in 55..110 -> 0.8
                in 110..Int.MAX_VALUE -> 1.0
                else -> 0.0
            }
            Constant.AirPollutionDataType.PM10 -> when (floor(components.pm10).toInt()) {
                in 0..25 -> 0.2
                in 25..50 -> 0.4
                in 50..90 -> 0.6
                in 90..180 -> 0.8
                in 180..Int.MAX_VALUE -> 1.0
                else -> 0.0
            }
            else -> 0.0
        }
    }?: 0.0
}