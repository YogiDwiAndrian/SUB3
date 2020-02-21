package com.yogi.moviecatalog

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.yogi.moviecatalog.Adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private var twiceExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
//
//
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        var searchView:SearchView?= null
//
//        searchView = menu.findItem(R.id.action_search).actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.maxWidth = Int.MAX_VALUE
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            /*
//            Gunakan method ini ketika search selesai atau OK
//             */
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return true
//            }
//
//            /*
//            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
//             */
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_search -> {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_bookmark -> {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.nav_pro_version -> {
                Toast.makeText(this, "Buy Pro Version clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rate -> {
                Toast.makeText(this, "Rate on Google Play clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_feedback -> {
                Toast.makeText(this, "Feedback clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_share -> {
                Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onRestart() {
        super.onRestart()
    }

    override fun onBackPressed() {
        if (twiceExit) {
            super.onBackPressed()
            return
        }

        this.twiceExit = true
        Toast.makeText(this, R.string.exittap, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ twiceExit = false }, 2000)
    }
}
