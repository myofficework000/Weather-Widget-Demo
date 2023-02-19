package com.example.weatherwidget.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentWeatherBinding
import com.example.weatherwidget.model.remote.Constant.ABSOLUTE_ZERO
import com.example.weatherwidget.model.remote.Constant.SHARED_PREF_CITY_KEY
import com.example.weatherwidget.model.remote.Constant.SHARED_PREF_FILE
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_weather.WeatherResponse
import com.example.weatherwidget.presenter.mvp_weather.MVPWeather
import com.example.weatherwidget.presenter.mvp_weather.WeatherPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class WeatherFragment : Fragment(), MVPWeather.WeatherView {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var presenter: MVPWeather.WeatherPresenter
    private lateinit var sharedPreferences: SharedPreferences
    private var cityName: String? = null
    private var sunsetValue: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSharedPreference()
        presenter = WeatherPresenter(VolleyHandler(this.context), this)
        presenter.getWeatherData(cityName ?: getString(R.string.default_city))
    }

    private fun initSharedPreference() {
        sharedPreferences =
            context?.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)!!
        cityName = sharedPreferences.getString(SHARED_PREF_CITY_KEY, "")
    }

    override fun setResult(weatherResponse: WeatherResponse) {
        val currentDate = {
            LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"))
        }
        val tempConverter = { a: Double -> (a - ABSOLUTE_ZERO).roundToInt() }

        binding.apply {
            weatherResponse.apply {
                city.text = cityName
                date.text = currentDate().toString()
                temperature.text = tempConverter(main.temp).toString()
                weatherDescription.text = weather[0].main
                minTemperature.text = tempConverter(main.temp_min).toString()
                maxTemperature.text = tempConverter(main.temp_max).toString()
            }
        }
    }

    fun updateLocation(city: String) {
        initSharedPreference()
        presenter.getWeatherData(city)
    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            Toast.makeText(this.context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}