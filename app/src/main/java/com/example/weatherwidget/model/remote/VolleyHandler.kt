package com.example.weatherwidget.model.remote

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherwidget.model.remote.Constant.API_KEY
import com.example.weatherwidget.model.remote.Constant.BASE_URL
import com.example.weatherwidget.model.remote.Constant.END_POINT_CITY_VALIDATE
import com.example.weatherwidget.model.remote.Constant.END_POINT_FORECAST
import com.example.weatherwidget.model.remote.Constant.END_POINT_WEATHER
import com.example.weatherwidget.model.remote.Constant.END_POINT_ZIPCODE
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.model.remote.data_forecast.OperationalCallbackForeCast
import com.example.weatherwidget.model.remote.data_weather.OperationalCallBackWeather
import com.example.weatherwidget.model.remote.data_weather.WeatherResponse
import com.example.weatherwidget.model.remote.data_zipcode.CityValidateResponse
import com.example.weatherwidget.model.remote.data_zipcode.OperationalCallbackCityValidate
import com.example.weatherwidget.model.remote.data_zipcode.OperationalCallbackZipCode
import com.example.weatherwidget.model.remote.data_zipcode.ZipcodeResponse
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution
import com.google.gson.Gson

class VolleyHandler(private val context: Context?) {
    private val requestQueue by lazy { Volley.newRequestQueue(context) }
    fun getWeatherData(city: String, callback: OperationalCallBackWeather) {
        val request = StringRequest(
            /*Request.Method.GET,
            "${Constant.BASE_URL}$END_POINT_WEATHER",*/
            Request.Method.GET,
            "$BASE_URL$END_POINT_CITY_VALIDATE?q=$city&appid=$API_KEY",
            {
                val response = Gson().fromJson(it, WeatherResponse::class.java)
                callback.onSuccess(response)
            },
            {
                callback.onFailure(it.toString())
            }
        )
        requestQueue.add(request)
    }

    fun getForecastData(city: String, callback: OperationalCallbackForeCast) {
        val request = StringRequest(
            Request.Method.GET,
            "$BASE_URL$END_POINT_FORECAST?q=$city&appid=$API_KEY",
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
    }

    fun getZipCodeData(zipCode: String, countryCode: String, callback: OperationalCallbackZipCode) {
        val request = StringRequest(
            Request.Method.GET,
            "$BASE_URL$END_POINT_ZIPCODE?zip=$zipCode,$countryCode&appid=$API_KEY",
            {
                try {
                    val apiResponse = Gson().fromJson(it, ZipcodeResponse::class.java)
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
    }

    fun getCityValidateData(city: String, callback: OperationalCallbackCityValidate) {
        val request = StringRequest(
            Request.Method.GET,
            "$BASE_URL$END_POINT_CITY_VALIDATE?q=$city&appid=$API_KEY",
            {
                try {
                    val apiResponse = Gson().fromJson(it, CityValidateResponse::class.java)
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
            { callback.onSuccess(Gson().fromJson(it, AirPollutionResponse::class.java)) },
            { callback.onError(it.message) }
        ).also { requestQueue.add(it) }
    }
}
