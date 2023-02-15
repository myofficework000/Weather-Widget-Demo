package com.example.weatherwidget.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherwidget.databinding.ActivityMainBinding
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.presenter.mvp_forecast.ForecastPresenter
import com.example.weatherwidget.presenter.mvp_forecast.MVPForecast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAirPollution.setOnClickListener {
            Intent(this, AirPollutionActivity::class.java).apply {
                // Btw this is Long Beach, Los Angeles.
                putExtra(
                    AirPollutionActivity.AIR_POLLUTION_LATITUDE_ARG,
                    Constant.PLACEHOLDER_COORDS.first
                )
                putExtra(
                    AirPollutionActivity.AIR_POLLUTION_LONGITUDE_ARG,
                    Constant.PLACEHOLDER_COORDS.second
                )
                startActivity(this)
            }
        }

        binding.btnForecast.setOnClickListener {
        }
    }
}