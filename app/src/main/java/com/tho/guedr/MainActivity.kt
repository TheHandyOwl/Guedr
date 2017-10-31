package com.tho.guedr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(TAG, "He pasado por onCreate")

        if (savedInstanceState != null) {
            Log.v(TAG, "savedInstanceState no es null y clave vale: ${savedInstanceState.getString("clave")}")
        } else {
            Log.v(TAG, "savedInstanceState ES null")
        }
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
