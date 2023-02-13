package com.example.weatherwidget.model.remote.data_airpollution

data class AirPollutionResponse(
    val coord: Coord,
    val list: List<PollutionData>
)