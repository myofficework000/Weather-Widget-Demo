package com.example.weatherwidget.model.remote

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherwidget.fragment.ForecastFragment
import com.example.weatherwidget.model.remote.Constant.BASE_URL
import com.example.weatherwidget.model.remote.Constant.END_POINT_WEATHER

import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.model.remote.data_weather.OperationalCallBackWeather
import com.example.weatherwidget.model.remote.data_weather.WeatherResponse
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution
import com.example.weatherwidget.model.remote.Constant.END_POINT_FORECAST
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.model.remote.data_forecast.OperationalCallbackForeCast
import com.google.gson.Gson

class VolleyHandler(private val context: Context?) {
    private val requestQueue by lazy { Volley.newRequestQueue(context) }
    fun getWeatherData(callback: OperationalCallBackWeather) {
         val request = StringRequest(
             Request.Method.GET,
             "${Constant.BASE_URL}$END_POINT_WEATHER",
             {
                 val response = Gson().fromJson(it, WeatherResponse::class.java)
                 callback.onSuccess(response)
             },
             {
                callback.onFailure(it.toString())
             }
         )
    }

    fun getForecastData(callback: OperationalCallbackForeCast) {
        val request = StringRequest(
            Request.Method.GET,
            BASE_URL+END_POINT_FORECAST,
            {
                try {
                    val apiResponse = Gson().fromJson(it, ForecastResponse::class.java)
                    callback.onSuccess(apiResponse)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            {
                callback.onFailure(it.toString())
            }
        )
        requestQueue.add(request)

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