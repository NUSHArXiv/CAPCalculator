package com.thepyprogrammer.capcalc

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.thepyprogrammer.capcalc.model.Database
import kotlinx.android.synthetic.main.settings_activity.*
import java.io.InputStream


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        
        val caps = arrayListOf(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, caps)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cap.adapter = adapter

        var database = Database.currentOccurence
        if(database == null) {
            val inputStream: InputStream = resources.openRawResource(R.raw.data)
            database = Database(inputStream)
        }

        


    }
}
