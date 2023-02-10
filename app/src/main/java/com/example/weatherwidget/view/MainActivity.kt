package com.example.weatherwidget.view

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
    }
}