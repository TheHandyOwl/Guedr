package com.tho.guedr

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class ForecastActivity : AppCompatActivity() {

    companion object {
        // Para saber de qué pantalla regresamos
        var REQUEST_UNITS = 1
    }

    lateinit var maxTemp: TextView
    lateinit var minTemp: TextView


    var forecast: Forecast? = null
        set(value) {
            field = value
            // Accedemos a las vistas de la interfaz
            val forecastImage = findViewById<ImageView>(R.id.forecast_image)
            maxTemp = findViewById<TextView>(R.id.max_temp)
            minTemp = findViewById<TextView>(R.id.min_temp)
            val humidity = findViewById<TextView>(R.id.humidity)
            val forecastDescription = findViewById<TextView>(R.id.forecast_description)

            // Actualizamos la vista con el modelo
            if (value != null) {
                forecastImage.setImageResource(value.icon)
                forecastDescription.text = value.description
                updateTemperature()
                val humidityString = getString(R.string.humidity_format, value.humidity)
                //humidity.setText(humidityString)
                humidity.text = humidityString
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        forecast = Forecast(25f, 10f, 35f, "Soleado con alguna nube", R.drawable.ico_01)

    }

    // Este método define qué opciones de menú tenemos
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.settings, menu)

        return true
    }

    // Este método dice que se hace una vez que se ha pulsado una opción de menú
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_show_settings) {
            // Aquí sabemos que se ha pulsado la opción de menú de mostrar ajustes
            //val intent = Intent(this, SettingsActivity::class.java)
            //val intent = SettingsActivity.intent(this)
            val units = if (temperatureUnits() == Forecast.TempUnit.CELSIUS)
                    R.id.celsius_rb
                else
                    R.id.farenheit_rb
            val intent = SettingsActivity.intent(this, units)

            // Esto lo haríamos si la segunda pantalla no nos tiene que devolver nada
            //startActivity(intent)

            //  Esto lo haríamos si la segunda pantalla nos tiene que devolver unos valores
            //startActivityForResult(intent, 1)
            startActivityForResult(intent, REQUEST_UNITS)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_UNITS ) {
            if (resultCode == Activity.RESULT_OK) {
                val unitSelected  = data?.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.celsius_rb)

                when(unitSelected) {
                    R.id.celsius_rb -> {
                        Log.v("TAG", "Soy ForecastActivity y han pulsado Ok y Celsius")
                    }
                    R.id.farenheit_rb -> Log.v("TAG", "Soy ForecastActivity y han pulsado OK y Fahrenheit")
                }

                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean(PREFERENCE_SHOW_CELSIUS, unitSelected == R.id.celsius_rb)
                        .apply()
                updateTemperature()

            } else {
                Log.v("TAG", "Soy ForecastActivity y han pulsado CANCEL")
            }
        }
    }

    //@SuppressLint("StringFormatMatches") //Cuando ha salido ésto aquí???
    private fun updateTemperature() {
        val units = temperatureUnits()
        val unitsString = temperatureUnitsString(units)
        //val maxTempString = getString(R.string.max_temp_format, forecast?.maxTemp, unitsString)
        //val minTempString = getString(R.string.min_temp_format, forecast?.minTemp, unitsString)
        val maxTempString = getString(R.string.max_temp_format, forecast?.getMaxTemp(units), unitsString)
        val minTempString = getString(R.string.min_temp_format, forecast?.getMinTemp(units), unitsString)
        maxTemp.text = maxTempString
        minTemp.text = minTempString
//        maxTemp?.setText(maxTempString)
//        minTemp?.setText(minTempString)

    }


    /**/
    private fun temperatureUnitsString(units: Forecast.TempUnit) = when (units) {
        Forecast.TempUnit.CELSIUS -> "ºC"
        else -> "F"
    }
    /**/

    //private fun temperatureUnitsString(units: Forecast.TempUnit) = if (units == Forecast.TempUnit.CELSIUS) "ºC" else "F"

    /*
    private fun temperatureUnits(): Forecast.TempUnit = when (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCE_SHOW_CELSIUS, true)) {
        true -> Forecast.TempUnit.CELSIUS
        false -> Forecast.TempUnit.FAHRENHEIT
    }
    */

    private fun temperatureUnits() = if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCE_SHOW_CELSIUS, true)) {
        Forecast.TempUnit.CELSIUS
    } else {
        Forecast.TempUnit.FAHRENHEIT
    }

}
