package com.example.weatherwidget.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentForecastBinding
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.Constant.ABSOLUTE_ZERO
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_forecast.ForecastResponse
import com.example.weatherwidget.presenter.mvp_forecast.ForecastPresenter
import com.example.weatherwidget.presenter.mvp_forecast.MVPForecast
import com.example.weatherwidget.view.adapter.ForecastAdapter
import kotlin.math.roundToInt

class ForecastFragment : Fragment(), MVPForecast, MVPForecast.ForecastView {
    private lateinit var binding: FragmentForecastBinding
    private lateinit var forecastPresenter: ForecastPresenter
    private lateinit var sharedPreferences: SharedPreferences
    private var cityName: String? = null

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
        initSharedPreference()
        forecastPresenter = ForecastPresenter(VolleyHandler(context), this)
        forecastPresenter.getForecast(cityName ?: "London")
    }

    private fun initSharedPreference() {
        sharedPreferences =
            context?.getSharedPreferences(Constant.SHARED_PREF_FILE, Context.MODE_PRIVATE)!!
        cityName = sharedPreferences.getString(Constant.SHARED_PREF_CITY_KEY, "")
    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setResult(forecastResponse: ForecastResponse) {
        val tempConverter = { a: Double -> (a - ABSOLUTE_ZERO).roundToInt() }

        binding.recyclerViewForecast.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewForecast.adapter = ForecastAdapter(forecastResponse.list)
        binding.apply {
            forecastResponse.apply {
                txtDegree.text =
                    getString(R.string.feels_like, tempConverter(list[0].main.feels_like))
                txtHuimdPercentage.text = list[0].main.humidity.toString()
                txtWind.text = forecastResponse.city.name
                txtmin.text =
                    tempConverter(list[0].main.temp_min - ABSOLUTE_ZERO).toString() + "° Min"
                txtmax.text =
                    tempConverter(list[0].main.temp_max - ABSOLUTE_ZERO).toString() + "° Max"
            }
        }
    }

    fun updateLocation(city: String) {
        initSharedPreference()
        forecastPresenter.getForecast(city)
    }
}