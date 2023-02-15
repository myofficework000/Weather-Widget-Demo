package com.example.weatherwidget.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root


}