package com.example.weatherwidget.model.remote.data_airpollution

data class PollutionData (
    val components: Components,
    val dt: Long,
    val main: Main
)