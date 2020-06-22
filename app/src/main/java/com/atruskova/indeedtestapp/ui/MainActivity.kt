package com.atruskova.indeedtestapp.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.atruskova.indeedtestapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)


        navView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_favorites -> {
                    if (!navController.popBackStack(R.id.navigation_favorites, false))
                        navController.navigate(R.id.navigation_favorites)
                    true
                }
                R.id.navigation_home -> {
                    if (!navController.popBackStack(R.id.navigation_home, false))
                        navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_user -> {
                    if (!navController.popBackStack(R.id.navigation_user, false))
                        navController.navigate(R.id.navigation_user)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}