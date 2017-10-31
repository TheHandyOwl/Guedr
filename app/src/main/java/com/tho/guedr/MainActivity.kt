package com.tho.guedr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Ponemos los botones como atributos
    var stoneButton: Button? = null
    var donkeyButton: Button? = null

    val TAG = MainActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazar controlador con vistas
        //findViewById<>()
        stoneButton = findViewById<Button>(R.id.stone_button)
        donkeyButton = findViewById<Button>(R.id.donkey_button)

        stoneButton?.setOnClickListener(this)
        donkeyButton?.setOnClickListener(this)

        Log.v(TAG, "He pasado por onCreate")

        if (savedInstanceState != null) {
            Log.v(TAG, "savedInstanceState no es null y clave vale: ${savedInstanceState.getString("clave")}")
        } else {
            Log.v(TAG, "savedInstanceState ES null")
        }
    }

    override fun onClick(v: View?) {
        Log.v(TAG, "Hemos pasado por onClick")
        // Solución 1
        /*
        if (v == stoneButton) {
            Log.v(TAG, "Hemos pulsado el botón piedra")
        } else {
            Log.v(TAG, "Hemos pulsado el botón burro")
        }
        */
        // Solución 2
        /*
        if (v != null) {
            if (v.getId() == R.id.stone_button){
                Log.v(TAG, "Hemos pulsado el botón piedra")
            } else if (v.getId() == R.id.donkey_button) {
                Log.v(TAG, "Hemos pulsado el botón burro")
            }
        }
        */
        // Solución 3
        /*
        if (v != null) {
            if (v.id == R.id.stone_button){
                Log.v(TAG, "Hemos pulsado el botón piedra")
            } else if (v.id == R.id.donkey_button) {
                Log.v(TAG, "Hemos pulsado el botón burro")
            }
        }
        */
        // Solución 4
        /*
        when (v?.id) {
            R.id.stone_button -> Log.v(TAG, "Hemos pulsado el botón piedra")
            R.id.donkey_button -> Log.v(TAG, "Hemos pulsado el botón burro")
            else -> Log.v(TAG, "No sé qué sé pulsó") // Este else es VOLUNTARIO
        }
        */
        // Solución 5
        Log.v(TAG, when (v?.id) {
            R.id.stone_button -> "Hemos pulsado el botón piedra"
            R.id.donkey_button -> "Hemos pulsado el botón burro"
            else -> "No sé qué sé pulsó" // Este else es OBLIGATORIO
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.v(TAG, "He pasado por onSaveInstanceState")

//        if (outState != null) {
//            //Aquí estamos seguros de que podemos llamar a métodos de outState sin NPE
//            outState.putString("clave", "valor")
//        }

        // El resultado es el mismo
        outState?.putString("clave", "valor")
    }

}
