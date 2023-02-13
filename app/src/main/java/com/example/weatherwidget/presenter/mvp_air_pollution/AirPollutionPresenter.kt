package com.example.weatherwidget.presenter.mvp_air_pollution

import androidx.appcompat.app.AlertDialog
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse

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
}