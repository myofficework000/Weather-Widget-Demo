package com.example.weatherwidget.presenter.mvp_zipcode

import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_zipcode.OperationalCallbackZipCode
import com.example.weatherwidget.model.remote.data_zipcode.ZipcodeResponse

class ZipcodePresenter(
    private val volleyHandler: VolleyHandler,
    private val zipCodeView: MVPZipCode.ZipcodeView
): MVPZipCode.ZipcodePresenter {
    override fun getZipcodeData(zipCode: String, countryCode: String) {
        zipCodeView.onLoad(true)
        volleyHandler.getZipCodeData(zipCode, countryCode, object : OperationalCallbackZipCode {
            override fun onSuccess(zipcodeResponse: ZipcodeResponse) {
                zipCodeView.onLoad(false)
                zipCodeView.setResult(zipcodeResponse)
            }

            override fun onFailure(message: String) {
                zipCodeView.showError(message)
            }
        })
    }
}