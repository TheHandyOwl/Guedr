package com.tho.guedr.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//OJO: no queremos la v7 sino la v13
import android.support.v13.app.FragmentPagerAdapter

import android.support.v4.view.ViewPager
import com.tho.guedr.R
import com.tho.guedr.fragment.ForecastFragment
import com.tho.guedr.model.Cities

class CityPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_pager)

        val pager = findViewById<ViewPager>(R.id.view_pager)
        val cities = Cities()
        val adapter = object: FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment = ForecastFragment.newInstance(cities[position])

            override fun getCount(): Int = cities.count

        }

        pager.adapter = adapter

    }
}
