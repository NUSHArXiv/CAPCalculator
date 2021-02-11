package com.thepyprogrammer.capcalc.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.Database

import com.thepyprogrammer.capcalc.ui.goals.GoalsFragment
import com.thepyprogrammer.capcalc.ui.home.HomeFragment
import com.thepyprogrammer.capcalc.ui.modules.ModulesFragment

import io.github.jitinsharma.bottomnavbar.model.NavObject

import kotlinx.android.synthetic.main.activity_main.*

import java.io.InputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var database = Database.currentOccurence
        if(database == null) {
            val inputStream: InputStream = resources.openRawResource(R.raw.data)
            database = Database(inputStream)
        }

        bottomBar.init(
            NavObject(
                    name = "",
                    image = ContextCompat.getDrawable(this, R.drawable.ic_add_black_24dp)!!
            ), listOf(
                NavObject(
                    name = "Home",
                    image = ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp)!!
                ),
                NavObject(
                    name = "Goals",
                    image = ContextCompat.getDrawable(this, R.drawable.ic_goals_black_24dp)!!
                ),
                NavObject(
                    name = "Modules",
                    image = ContextCompat.getDrawable(this, R.drawable.ic_dashboard_black_24dp)!!
                )
            )
        ) { position, primaryClicked ->
            when(position) {
                0 -> showHomeFragment()
                1 -> showGoalsFragment()
                2 -> showModulesFragment()
                else -> if (primaryClicked) showModulesFragment()
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun showHomeFragment() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment)
            .commit()
    }

    @SuppressLint("PrivateResource")
    private fun showGoalsFragment() {
        val fragment = GoalsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment)
            .commit()
    }

    @SuppressLint("PrivateResource")
    private fun showModulesFragment() {
        val fragment = ModulesFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.container, fragment)
            .commit()
    }

}