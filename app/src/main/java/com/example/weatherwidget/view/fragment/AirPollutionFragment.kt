package com.example.weatherwidget.view.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentAirPollutionBinding
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.presenter.mvp_air_pollution.AirPollutionPresenter
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution

class AirPollutionFragment : Fragment(), MVPAirPollution.IView {
    private lateinit var binding: FragmentAirPollutionBinding
    private val presenter by lazy {
        AirPollutionPresenter(this, VolleyHandler(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAirPollutionBinding.inflate(inflater, container, false).apply {
        binding = this
        airPollutionSummaryBarBackground.background = Constant.AIRPOLLUTIONBARGRADIENT
        presenter.getAirPollutionData(
            Constant.PLACEHOLDER_COORDS.first,
            Constant.PLACEHOLDER_COORDS.second
        )
    }.root

    override fun onSuccess(data: AirPollutionResponse?) {
        data?.let {
            with (it.list.maxByOrNull { pData -> pData.dt }) {
                binding.airPollutionSummaryText.text =
                    if (this == null) getString(R.string.api_no_data_text)
                    else presenter.getAirPollutionSummaryText(main.aqi)
                if (this != null) binding.airPollutionSummaryBar.value = main.aqi.toFloat() / 10
            }
        }
    }

    override fun onError(message: String?) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.api_error_title))
            .setMessage(getString(R.string.api_error_message))
            .setNegativeButton(getString(R.string.negative_button_1)) { _, _->}
            .show()
    }
}