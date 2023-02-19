package com.example.weatherwidget.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentDashboardBinding
import com.example.weatherwidget.view.fragment.AirPollutionFragment
import com.example.weatherwidget.view.fragment.WeatherFragment

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    val airPollutionFragment by lazy{
        binding.airPollutionFragmentView.getFragment<AirPollutionFragment>()
    }
    val weatherFragment by lazy{
        binding.weatherFragmentView.getFragment<WeatherFragment>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root


}