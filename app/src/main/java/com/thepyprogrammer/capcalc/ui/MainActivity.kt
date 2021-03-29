package com.thepyprogrammer.capcalc.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.ModuleDatabase

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var database = if (ModuleDatabase.currentOccurence != null) ModuleDatabase.currentOccurence
        else ModuleDatabase(resources.openRawResource(R.raw.data))

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_goals, R.id.nav_modules
            )
        )

        val navController = findNavController(R.id.nav_host_fragment).apply {
            setupActionBarWithNavController(this, appBarConfiguration)
        }

        bottom_navigation.apply {
            setupWithNavController(navController)
            background = null
            for (i in 0..3) menu.getItem(0).isEnabled = true
        }
    }

}