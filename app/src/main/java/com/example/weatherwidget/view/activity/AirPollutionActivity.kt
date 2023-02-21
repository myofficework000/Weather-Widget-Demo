package com.example.weatherwidget.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.ActivityAirPollutionBinding
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.presenter.mvp_air_pollution.AirPollutionPresenter
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution

class AirPollutionActivity : AppCompatActivity(), MVPAirPollution.IView {
    private val binding by lazy { ActivityAirPollutionBinding.inflate(layoutInflater) }
    private val presenter by lazy { AirPollutionPresenter(this, VolleyHandler(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        intent.extras?.let {
            val requestLatitude = it.getDouble(AIR_POLLUTION_LATITUDE_ARG, Double.NEGATIVE_INFINITY)
            val requestLongitude = it.getDouble(AIR_POLLUTION_LONGITUDE_ARG, Double.NEGATIVE_INFINITY)
            if (requestLatitude != Double.NEGATIVE_INFINITY &&
                requestLongitude != Double.NEGATIVE_INFINITY)
                presenter.getAirPollutionData(requestLatitude, requestLongitude)
        }
    }


    override fun onSuccess(data: AirPollutionResponse?) {
        data?.let {
            val recentAqhi = it.list.maxByOrNull { pData -> pData.dt }
            binding.airPollutionSummaryValue.text = recentAqhi
                ?.run{ main.aqi.toString() }
                ?:getString(R.string.api_no_data_text)
        }
    }

    override fun onError(message: String?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.api_error_title))
            .setMessage(getString(R.string.api_error_message))
            .setNegativeButton(getString(R.string.negative_button_1)) { _, _->}
            .show()
        println(message)
    }

    companion object {
        const val AIR_POLLUTION_LATITUDE_ARG = "latitude"
        const val AIR_POLLUTION_LONGITUDE_ARG = "longitude"
    }
}