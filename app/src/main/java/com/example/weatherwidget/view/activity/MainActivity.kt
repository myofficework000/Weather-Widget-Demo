package com.example.weatherwidget.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAirPollution.setOnClickListener {
            Intent(this, AirPollutionActivity::class.java).apply {
                // Btw this is Long Beach, Los Angeles.
                putExtra(AirPollutionActivity.AIR_POLLUTION_LATITUDE_ARG, 33.7701)
                putExtra(AirPollutionActivity.AIR_POLLUTION_LONGITUDE_ARG, 118.1937)
                startActivity(this)
            }
        }
    }
}