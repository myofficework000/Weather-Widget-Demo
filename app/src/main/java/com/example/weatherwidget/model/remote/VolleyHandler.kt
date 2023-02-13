package com.example.weatherwidget.model.remote

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution
import com.google.gson.Gson

class VolleyHandler(private val context: Context) {
    private val requestQueue by lazy { Volley.newRequestQueue(context) }

    fun getWeatherData() {

    }

    fun getForecastData() {

    }

    fun getZipCodeData() {

    }

    fun getAirPollutionData(
        latitude: Double,
        longitude: Double,
        callback: MVPAirPollution.ICallback
    ) {
        StringRequest(
            Request.Method.GET,
            Constant.BASE_URL + Constant.END_POINT_AIR_POLLUTION +
                    "?lat=$latitude" +
                    "&lon=$longitude" +
                    "&appid=${Constant.API_KEY}",
            { callback.onSuccess( Gson().fromJson(it, AirPollutionResponse::class.java) ) },
            { callback.onError(it.message) }
        ).also { requestQueue.add(it) }
    }

}