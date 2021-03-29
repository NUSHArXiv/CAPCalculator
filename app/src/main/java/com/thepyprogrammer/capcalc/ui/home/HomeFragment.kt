package com.thepyprogrammer.capcalc.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.calculations.GradCAP
import com.thepyprogrammer.capcalc.model.calculations.PromoCAP
import com.thepyprogrammer.capcalc.model.data.CAPModule
import com.thepyprogrammer.capcalc.model.gson.GsonModule
import com.thepyprogrammer.capcalc.ui.home.adapter.HomeAdapter
import java.io.File
import java.io.IOException
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val modules = readModuleFile()

        val homeAdapter = HomeAdapter(this,
            mutableListOf(
                GradCAP(modules).cap,
                PromoCAP(modules).cap
            )
        )

        val home_feed = root.findViewById<RecyclerView>(R.id.home_feed)
        home_feed.adapter = homeAdapter
        home_feed.layoutManager = LinearLayoutManager(activity)

        return root
    }

    private fun readModuleFile(): MutableList<CAPModule> =
        try {
            val dbFile = File(requireActivity().filesDir.path.toString() + "/modules.json")
            if (!dbFile.exists()) dbFile.createNewFile()
            val sc = Scanner(dbFile)
            var s = ""
            val gson = Gson()
            while (sc.hasNext()) {
                s += sc.nextLine()
            }
            sc.close()
            val sType = object : TypeToken<List<GsonModule>>() {}.type
            val list = mutableListOf<CAPModule>()
            gson.fromJson<List<GsonModule>>(s, sType)?.forEach {
                list.add(it.toModule())
            }
            list
        } catch (ex: IOException) {
            Log.i("ERR", ex.stackTrace.toString())
            mutableListOf()
        }
}