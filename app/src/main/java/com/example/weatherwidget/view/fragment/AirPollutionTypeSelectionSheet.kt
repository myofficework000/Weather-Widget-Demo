package com.example.weatherwidget.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.weatherwidget.databinding.FragmentAirPollutionTypeSelectionBinding
import com.example.weatherwidget.model.remote.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AirPollutionTypeSelectionSheet(
    private val selectedData: Constant.AirPollutionDataType,
    private val callback: (Constant.AirPollutionDataType) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAirPollutionTypeSelectionBinding
    private lateinit var optionsMap: HashMap<Constant.AirPollutionDataType, AppCompatButton>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAirPollutionTypeSelectionBinding.inflate(inflater, container, false).apply{
        binding = this
        optionsMap = hashMapOf(
            Constant.AirPollutionDataType.CAQI to airPollutionTypeCaqi,
            Constant.AirPollutionDataType.CO to airPollutionTypeCo,
            Constant.AirPollutionDataType.NO to airPollutionTypeNo,
            Constant.AirPollutionDataType.NO2 to airPollutionTypeNo2,
            Constant.AirPollutionDataType.O3 to airPollutionTypeO3,
            Constant.AirPollutionDataType.SO2 to airPollutionTypeSo2,
            Constant.AirPollutionDataType.PM2_5 to airPollutionTypePm25,
            Constant.AirPollutionDataType.PM10 to airPollutionTypePm10,
            Constant.AirPollutionDataType.NH3 to airPollutionTypeNh3
        ).onEach { item ->
            item.value.setOnClickListener {
                callback(item.key)
                dismiss()
            }
        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        optionsMap[selectedData]?.isEnabled = false
    }
}