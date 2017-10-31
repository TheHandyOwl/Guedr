package com.tho.guedr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class ForecastActivity : AppCompatActivity() {

    var forecast: Forecast? = null
        set(value) {
            // Accedemos a las vistas de la interfaz
            val forecastImage = findViewById<ImageView>(R.id.forecast_image)
            val maxTemp = findViewById<TextView>(R.id.max_temp)
            val minTemp = findViewById<TextView>(R.id.min_temp)
            val humidity = findViewById<TextView>(R.id.humidity)
            val forecastDescription = findViewById<TextView>(R.id.forecast_description)

            // Actualizamos la vista con el modelo
            forecastImage.setImageResource(forecast.icon)
            forecastDescription.text(forecast.description)
            val maxTempString = getString(R.string.max_temp_format, forecast.maxTemp)
            val minTempString = getString(R.string.min_temp_format, forecast.minTemp)
            val humidityString = getString(R.string.humidity_format, forecast.humidity)
            maxTemp.setText(maxTempString)
            minTemp.setText(minTempString)
            humidity.setText(humidityString)
        }

    val TAG = ForecastActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecast = Forecast(25f, 10f, 35f, "Soleado con alguna nube", R.drawable.ico_01)

    }

}