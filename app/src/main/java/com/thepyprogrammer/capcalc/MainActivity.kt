package com.thepyprogrammer.capcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thepyprogrammer.capcalc.model.Database
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

    }
}