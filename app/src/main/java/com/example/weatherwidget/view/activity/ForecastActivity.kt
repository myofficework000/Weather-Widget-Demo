package com.example.weatherwidget.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherwidget.databinding.ActivityForecastBinding
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.presenter.mvp_forecast.MVPForecast

class ForecastActivity : AppCompatActivity(), MVPForecast.ForecastView {
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onLoad(isLoading: Boolean) {
    }

    override fun showError(message: String) {
    }

    override fun setResult(forecastResponse: ForecastResponse) {
    }
}