package com.example.weatherwidget.fragment

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentForecastBinding
import com.example.weatherwidget.model.remote.Constant.ABSOLUTE_ZERO
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.presenter.mvp_forecast.ForecastPresenter
import com.example.weatherwidget.presenter.mvp_forecast.MVPForecast
import com.example.weatherwidget.view.adapter.ForecastAdapter
import kotlin.math.nextDown

class ForecastFragment : Fragment(), MVPForecast, MVPForecast.ForecastView {
    private lateinit var binding: FragmentForecastBinding
    private lateinit var forecastPresenter: ForecastPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentForecastBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initForecastPresenter()
    }

    private fun initForecastPresenter() {
        forecastPresenter = ForecastPresenter(VolleyHandler(context), this)
        forecastPresenter.getForecast()
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setResult(forecastResponse: ForecastResponse) {
        binding.recyclerViewForecast.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewForecast.adapter = ForecastAdapter(forecastResponse.list)

        binding.txtDegree.text = Math.round(forecastResponse.list[0].main.feels_like-ABSOLUTE_ZERO).toString() + "°C"
        binding.txtHuimdPercentage.text = forecastResponse.list[0].main.humidity.toString()
        binding.txtWind.text = forecastResponse.city.name
        binding.txtmin.text = Math.round(forecastResponse.list[0].main.temp_min-ABSOLUTE_ZERO).toString() + "° Min"
        binding.txtmax.text = Math.round(forecastResponse.list[0].main.temp_max-ABSOLUTE_ZERO).toString() + "° Max"


    }
}