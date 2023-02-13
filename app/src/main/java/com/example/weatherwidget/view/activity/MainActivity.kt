package com.example.weatherwidget.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherwidget.databinding.ActivityMainBinding
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.presenter.mvp_forecast.ForecastPresenter
import com.example.weatherwidget.presenter.mvp_forecast.MVPForecast

class MainActivity : AppCompatActivity(),MVPForecast, MVPForecast.ForecastView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var forecastPresenter: ForecastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initForecastPresenter()
        binding.btnForecast.setOnClickListener {
            forecastPresenter.getForecast()
        }
    }
    private fun initForecastPresenter() {
       forecastPresenter = ForecastPresenter(VolleyHandler(this),this)
    }

    override fun onLoad(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setResult(forecastResponse: ForecastResponse) {
        TODO("Not yet implemented")
    }
}