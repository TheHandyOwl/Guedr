package com.tho.guedr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup

class SettingsActivity: AppCompatActivity() {

    var radioGroup : RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Esto sería un equivalente a una clase anónima en Kotlin (para los "Javeros" ;-))
//        findViewById<View>(R.id.ok_button).setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                // Aquí iría el código de aceptar
//                acceptSettings()
//            }
//        })

        //Sol 2
//        findViewById<View>(R.id.ok_btn).setOnClickListener { v ->
//            acceptSettings()
//        }
        //Sol 3
//        findViewById<View>(R.id.ok_btn).setOnClickListener {
//            acceptSettings()
//        }
        //Sol 4
        findViewById<View>(R.id.ok_btn).setOnClickListener { acceptSettings() }
        findViewById<View>(R.id.cancel_btn).setOnClickListener { cancelSettings() }

        radioGroup = findViewById(R.id.units_rg)

    }

    private fun cancelSettings() {
        // Finalizamos esta actividad, regresando a la anterior
        finish()
    }

    private fun acceptSettings() {
        // Finalizamos esta actividad, regresando a la anterior
        finish()
    }

}