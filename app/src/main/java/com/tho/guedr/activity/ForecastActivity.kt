package com.tho.guedr.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tho.guedr.R
import com.tho.guedr.fragment.CityListFragment
import com.tho.guedr.model.Cities
import com.tho.guedr.model.City

class ForecastActivity : AppCompatActivity(), CityListFragment.OnCitySelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
        if (fragmentManager.findFragmentById(R.id.city_list_fragment) == null) {
            val fragment = CityListFragment.newInstance(Cities())
            fragmentManager
                    .beginTransaction()
                    .add(R.id.city_list_fragment, fragment)
                    .commit()
        }
    }

    override fun OnCitySelected(city: City?, position: Int) {

    }

}
