package com.example.weatherwidget.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentAirPollutionBinding
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_airpollution.AirPollutionResponse
import com.example.weatherwidget.model.remote.data_airpollution.PollutionData
import com.example.weatherwidget.presenter.mvp_air_pollution.AirPollutionPresenter
import com.example.weatherwidget.presenter.mvp_air_pollution.MVPAirPollution

class AirPollutionFragment : Fragment(), MVPAirPollution.IView {
    private lateinit var binding: FragmentAirPollutionBinding
    private val presenter by lazy {
        AirPollutionPresenter(this, VolleyHandler(requireContext()))
    }
    private var selectedDataType = Constant.AirPollutionDataType.CAQI
    private var pollutionData: List<PollutionData>? = null
    private var mostRecentPollutionData: PollutionData? = null

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
        airPollutionSelectDataTypeButton.setOnClickListener {
            AirPollutionTypeSelectionSheet(selectedDataType) {
                when (it) {
                    Constant.AirPollutionDataType.CAQI -> updateSummary(it)
                    Constant.AirPollutionDataType.NO2 -> updateSummary(it)
                    Constant.AirPollutionDataType.O3 -> updateSummary(it)
                    Constant.AirPollutionDataType.PM2_5 -> updateSummary(it)
                    Constant.AirPollutionDataType.PM10 -> updateSummary(it)
                    else -> {}
                }
                selectedDataType = it
            }.show(parentFragmentManager, "")
        }
    }.root

    override fun onSuccess(data: AirPollutionResponse?) {
        pollutionData = data?.list
        mostRecentPollutionData = pollutionData?.let {
            it.maxBy { pData -> pData.dt }
        }
        updateSummary(selectedDataType)
    }

    override fun onError(message: String?) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.api_error_title))
            .setMessage(getString(R.string.api_error_message))
            .setNegativeButton(getString(R.string.negative_button_1)) { _, _->}
            .show()
    }

    private fun updateSummary(dataType: Constant.AirPollutionDataType) {
        pollutionData?.let {
            if (it.isNotEmpty()) {
                val mostRecentDataFraction = presenter.getAirPollutionDataFraction(
                    dataType, mostRecentPollutionData)
                binding.airPollutionSummaryText.text =
                    presenter.getAirPollutionSummaryText((mostRecentDataFraction * 5).toInt())
                ObjectAnimator.ofFloat(
                        binding.airPollutionSummaryBar,
                        "value",
                        mostRecentDataFraction.toFloat())
                    .apply {
                        duration = 500
                        interpolator = DecelerateInterpolator()
                        start()
                    }
                binding.airPollutionSummaryBar.value = mostRecentDataFraction.toFloat()
                binding.airPollutionSummaryType.text = getAirPollutionSummaryLabel(dataType)
            } else {
                binding.airPollutionSummaryText.text = getString(R.string.api_no_data_text)
            }
        }
    }

    private fun getAirPollutionSummaryLabel(dataType: Constant.AirPollutionDataType) =
        when(dataType) {
            Constant.AirPollutionDataType.CAQI -> resources.getString(R.string.airPollutionCaqi)
            Constant.AirPollutionDataType.NO2 -> resources.getString(R.string.airPollutionNo2)
            Constant.AirPollutionDataType.O3 -> resources.getString(R.string.airPollutionO3)
            Constant.AirPollutionDataType.PM2_5 -> resources.getString(R.string.airPollutionPm2_5)
            Constant.AirPollutionDataType.PM10 -> resources.getString(R.string.airPollutionPm10)
            else -> ""
        }
}