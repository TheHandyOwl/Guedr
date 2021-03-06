package com.tho.guedr.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.tho.guedr.model.Forecast
import com.tho.guedr.PREFERENCE_SHOW_CELSIUS
import com.tho.guedr.R
import com.tho.guedr.activity.SettingsActivity
import com.tho.guedr.activity.ForecastActivity
import com.tho.guedr.model.City

class ForecastFragment : Fragment() {

    companion object {
        val REQUEST_UNITS = 1
        private val ARG_CITY = "ARG_CITY"

        fun newInstance(city: City): ForecastFragment {
            val fragment = ForecastFragment()

            val arguments = Bundle()
            // Como no podemos pasar City pasamos un serializable
            // OJO: todos los atributos tienen que ser serializables
            arguments.putSerializable(ARG_CITY, city)
            fragment.arguments = arguments

            return fragment
        }
    }

    val TAG = ForecastActivity::class.java.canonicalName

    lateinit var root: View
    lateinit var maxTemp: TextView
    lateinit var minTemp: TextView

    var city: City? = null
        set(value) {
            if (value != null) {
                root.findViewById<TextView>(R.id.city).setText(value.name)
                forecast = value.forecast
            }
        }

    var forecast: Forecast? = null
        set(value) {
            field = value
            // Accedemos a las vistas de la interfaz
            val forecastImage = root.findViewById<ImageView>(R.id.forecast_image)
            maxTemp = root.findViewById(R.id.max_temp)
            minTemp = root.findViewById(R.id.min_temp)
            val humidity = root.findViewById<TextView>(R.id.humidity)
            val forecastDescription = root.findViewById<TextView>(R.id.forecast_description)

            // Actualizamos la vista con el modelo
            value?.let {
                forecastImage.setImageResource(value.icon)
                forecastDescription.text = value.description
                updateTemperature()
                val humidityString = getString(R.string.humidity_format, value.humidity)
                humidity.text = humidityString
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        inflater?.let {
            // it se refiere a la variable, que no puede ser nombrada dentro del let
            root = it.inflate(R.layout.fragment_forecast, container, false)
            if (arguments != null) {
                city = arguments.getSerializable(ARG_CITY) as City
            }
        }

        return root
    }

    // Este método define qué opciones de menú tenemos
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.settings, menu)
    }

    // Este método dice que se hace una vez que se ha pulsado una opción de menú
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_show_settings) {
            // Aquí sabemos que se ha pulsado la opción de menú de mostrar ajustes
            //val intent = SettingsActivity.intent(this)
            val units = if (temperatureUnits() == Forecast.TempUnit.CELSIUS) {
                R.id.celsius_rb
            } else {
                R.id.fahrenheit_rb
            }
            val intent = SettingsActivity.intent(activity, units)

            // Esto lo haríamos si la segunda pantalla no nos tiene que devolver nada
            //startActivity(intent)

            // Esto lo haríamos si la segunda pantalla nos tiene que devolver unos valores
            startActivityForResult(intent, REQUEST_UNITS)

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_UNITS) {
            if (resultCode == Activity.RESULT_OK) {
                val unitSelected = data?.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.celsius_rb)
                when (unitSelected) {
                    R.id.celsius_rb -> {
                        Log.v(TAG, "Soy ForecastActivity, han pulsado OK y las unidades son Celsius")
                        //Toast.makeText(this,"Celsius seleccionado", Toast.LENGTH_LONG).show()
                    }
                    R.id.fahrenheit_rb -> {
                        Log.v(TAG, "Soy ForecastActivity, han pulsado OK y las unidades son Fahrenheit")
                        //Toast.makeText(this,"Fahrenheit seleccionado", Toast.LENGTH_LONG).show()
                    }
                }

                val oldShowCelsius = temperatureUnits()

                PreferenceManager.getDefaultSharedPreferences(activity)
                        .edit()
                        //.putBoolean(PREFERENCE_SHOW_CELSIUS, true)
                        .putBoolean(PREFERENCE_SHOW_CELSIUS, unitSelected == R.id.celsius_rb)
                        .apply()
                updateTemperature()

                Snackbar.make(
                        root,
                        "Han cambiado las preferencias",
                        Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", {
                            PreferenceManager.getDefaultSharedPreferences(activity)
                                    .edit()
                                    //.putBoolean(PREFERENCE_SHOW_CELSIUS, true)
                                    .putBoolean(PREFERENCE_SHOW_CELSIUS, oldShowCelsius == Forecast.TempUnit.CELSIUS)
                                    .apply()
                            updateTemperature()
                        })
                        .show()
            } else {
                Log.v(TAG,"Soy ForecastActivity y han pulsado CANCEL")
            }
        }
    }

    // Para saber si, estando en un ViewPager por ejemplo, debemos refrescar las unidades de las temperaturas
    // Es algo así como el viewWillAppear de los Fragment
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && forecast != null) {
            updateTemperature()
        }
    }

    private fun updateTemperature() {
        val units = temperatureUnits()
        val unitsString = temperatureUnitsString(units)
        val maxTempString = getString(R.string.max_temp_format, forecast?.getMaxTemp(units), unitsString)
        val minTempString = getString(R.string.min_temp_format, forecast?.getMinTemp(units), unitsString)
        maxTemp.text = maxTempString
        minTemp.text = minTempString
    }

    private fun temperatureUnitsString(units: Forecast.TempUnit) = when (units) {
        Forecast.TempUnit.CELSIUS -> "ºC"
        else -> "F"
    }

    private fun temperatureUnits() = if (PreferenceManager.getDefaultSharedPreferences(activity)
            .getBoolean(PREFERENCE_SHOW_CELSIUS, true)) {
        Forecast.TempUnit.CELSIUS
    }
    else {
        Forecast.TempUnit.FAHRENHEIT
    }
}