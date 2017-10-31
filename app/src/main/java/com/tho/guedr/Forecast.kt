package com.tho.guedr

class Forecast(var _maxTemp: Float, var _minTemp: Float, var _description: String, var _icon: Int) {

    fun getMaxTemp(): Float {
        return _maxTemp
    }

    fun setMaxTemp(value: Float) {
        _maxTemp = value
    }

}
