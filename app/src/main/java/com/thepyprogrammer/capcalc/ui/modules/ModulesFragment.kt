package com.thepyprogrammer.capcalc.ui.modules

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.ModuleDatabase
import com.thepyprogrammer.capcalc.model.data.CAPModule
import com.thepyprogrammer.capcalc.model.gson.GsonModule
import com.thepyprogrammer.capcalc.model.gson.GsonModules
import com.thepyprogrammer.capcalc.ui.modules.adapter.ModuleAdapter
import com.thepyprogrammer.capcalc.ui.modules.adapter.ModuleSwipeToDeleteCallback
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.util.*


class ModulesFragment : Fragment() {

    private lateinit var moduleAdapter: ModuleAdapter
    private lateinit var moduleViewModel: ModuleViewModel


    private val caps = arrayListOf(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moduleViewModel = ViewModelProvider(this).get(ModuleViewModel::class.java)

        moduleViewModel.moduleList.value = readFile()

        val root = inflater.inflate(R.layout.fragment_modules, container, false)

        val capselector: Spinner = root.findViewById(R.id.capselector)
        val autocomplete: AutoCompleteTextView = root.findViewById(R.id.autocomplete)
        val add: Button = root.findViewById(R.id.add)
        val module_list: RecyclerView = root.findViewById(R.id.module_list)

        val spinnerAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                caps
            )
        }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        capselector.adapter = spinnerAdapter


        var database = ModuleDatabase.currentOccurence
        if (database == null) {
            val inputStream: InputStream = resources.openRawResource(R.raw.data)
            database = ModuleDatabase(inputStream)
        }

        val autocompleteAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                database.getFullNames()
            )
        }
        autocompleteAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autocomplete.setAdapter(autocompleteAdapter)

        moduleAdapter = ModuleAdapter(this, moduleViewModel.moduleList.value!!)

        module_list.adapter = moduleAdapter
        module_list.layoutManager = LinearLayoutManager(activity)
        val itemTouchHelper = ItemTouchHelper(ModuleSwipeToDeleteCallback(moduleAdapter))
        itemTouchHelper.attachToRecyclerView(module_list)



        add.setOnClickListener {
            val module = database.getModuleByFullName(autocomplete.text.toString())
            val cap: Double = capselector.selectedItem as Double
            if (module != null) {
                database.modules[module] = cap
                moduleAdapter.addModule(module, cap)
                autocomplete.text.clear()
            }
        }
        return root
    }

    private fun readFile(): MutableList<CAPModule> {
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
            return list
        } catch (ex: IOException) {
            Log.i("ERR", ex.stackTrace.toString())
            return mutableListOf()
        }
    }

    fun updateFile() {
        try {
            val gModules = GsonModules(moduleAdapter.modules)
            val gson = Gson()
            val jsonString = gson.toJson(gModules)
            val dbFile = File(requireActivity().filesDir.path.toString() + "/modules.json")
            if (!dbFile.exists()) dbFile.createNewFile()
            val pw = PrintWriter(dbFile)
            pw.println(jsonString)
            pw.close()
        } catch (ex: IOException) {
            Log.i("ERR", ex.stackTrace.toString())
        }
    }

    override fun onDestroy() {
        updateFile()
        super.onDestroy()
    }
}