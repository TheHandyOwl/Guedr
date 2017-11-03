package com.tho.guedr

data class Forecast(val maxTemp: Float, val minTemp: Float, val humidity: Float, val description: String, val icon: Int) {
    enum class TempUnit {
        CELSIUS,
        FAHRENHEIT
    }
}
