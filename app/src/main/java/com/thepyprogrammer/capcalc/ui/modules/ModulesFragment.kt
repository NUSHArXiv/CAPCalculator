package com.thepyprogrammer.capcalc.ui.modules

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thepyprogrammer.capcalc.MainActivity
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.Database
import com.thepyprogrammer.capcalc.model.Module
import kotlinx.android.synthetic.main.fragment_modules.*
import java.io.InputStream


class ModulesFragment : Fragment() {

    private lateinit var moduleViewModel: ModuleViewModel

    companion object {
        val modules = HashMap<Module, Double>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val caps = arrayListOf(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0)
        val main = activity as MainActivity?
        val spinnerAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                caps
            )
        }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        capselector.adapter = spinnerAdapter



        var database = Database.currentOccurence
        if(database == null) {
            val inputStream: InputStream = resources.openRawResource(R.raw.data)
            database = Database(inputStream)
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

        add.setOnClickListener{
            val module = database.get(autocomplete.listSelection)
            val cap: Double = capselector.selectedItem as Double
            if(module != null) {
                modules.put(module, cap)

                val row = TableRow(activity)
                row.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )

                val padding = resources.getDimensionPixelOffset(R.dimen.table_text_padding)

                val codeView = TextView(activity);
                codeView.layoutParams = TableRow.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    R.dimen.rowHeight
                ).apply {
                    gravity = Gravity.CENTER
                }
                codeView.text = module.code;
                codeView.setPadding(padding, padding, padding, padding)
                codeView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                codeView.textSize = resources.getDimension(R.dimen.table_text_size)

                row.addView(codeView);


                val capView = TextView(activity);
                capView.layoutParams = TableRow.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    R.dimen.rowHeight
                ).apply {
                    gravity = Gravity.CENTER
                }
                capView.text = "$cap"
                capView.setPadding(padding, padding, padding, padding)
                capView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                capView.textSize = resources.getDimension(R.dimen.table_text_size)

                row.addView(codeView);
            }


        }


        moduleViewModel = ViewModelProvider(this).get(ModuleViewModel::class.java)
        return inflater.inflate(R.layout.fragment_modules, container, false)
    }
}