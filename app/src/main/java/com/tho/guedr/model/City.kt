package com.tho.guedr.model

import com.tho.guedr.Forecast
import java.io.Serializable

data class City(var name: String, var forecast: Forecast): Serializable {
    override fun toString() = name
}