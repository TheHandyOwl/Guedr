    package com.tho.guedr.activity

    import android.app.Fragment
    import android.support.v7.app.AppCompatActivity
    import android.os.Bundle
    //OJO: no queremos la v7 sino la v13
    import android.support.v13.app.FragmentPagerAdapter

    import android.support.v4.view.ViewPager
    import android.support.v7.widget.Toolbar
    import android.view.Menu
    import android.view.MenuItem
    import com.tho.guedr.R
    import com.tho.guedr.fragment.ForecastFragment
    import com.tho.guedr.model.Cities

    class CityPagerActivity : AppCompatActivity() {

        val pager by lazy { findViewById<ViewPager>(R.id.view_pager) }
        val cities = Cities()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_city_pager)

            // Configuramos la Toolbar
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            // Icono de la app
            toolbar.setLogo(R.mipmap.ic_launcher)
            setSupportActionBar(toolbar)

            val pager = findViewById<ViewPager>(R.id.view_pager)
            val adapter = object: FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int): Fragment = ForecastFragment.newInstance(cities[position])

                override fun getCount(): Int = cities.count

                override fun getPageTitle(position: Int)= cities[position].name

            }

            pager.adapter = adapter

            // Para cambiar el título al cambiar de página necesitamos un listener
            pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) { }
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
                override fun onPageSelected(position: Int) {
                    updateCityInfo(position)
                }
            })

        }

        fun updateCityInfo(position: Int) {
            supportActionBar?.title = cities[position].name
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            super.onCreateOptionsMenu(menu)
            menuInflater.inflate(R.menu.pager, menu)

            return true
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.previous -> {
                    pager.currentItem = pager.currentItem -1
                    true
                }
                R.id.next -> {
                    pager.currentItem++
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

        override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
            super.onPrepareOptionsMenu(menu)

            invalidateOptionsMenu()

            val menuPrev = menu?.findItem(R.id.previous)
            menuPrev?.setEnabled(pager.currentItem > 0)

            val menuNext = menu?.findItem(R.id.next)
            menuNext?.setEnabled(pager.currentItem < cities.count - 1)

            return true

        }

    }