package com.example.weatherwidget.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherwidget.databinding.ActivityMainBinding
import com.example.weatherwidget.presenter.mvp_forecast.ForecastPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var forecastPresenter: ForecastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}