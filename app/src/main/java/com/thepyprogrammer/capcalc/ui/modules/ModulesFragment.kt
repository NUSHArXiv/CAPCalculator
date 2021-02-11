package com.thepyprogrammer.capcalc.ui.modules

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evrencoskun.tableview.TableView
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.Database
import java.io.InputStream


class ModulesFragment : Fragment() {

    private var padding: Int = 0
    private lateinit var moduleViewModel: ModuleViewModel


    val caps = arrayListOf(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moduleViewModel = ViewModelProvider(this).get(ModuleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_modules, container, false)

        val capselector: Spinner = root.findViewById(R.id.capselector)
        val autocomplete: AutoCompleteTextView = root.findViewById(R.id.autocomplete)
        val add: Button = root.findViewById(R.id.add)
        
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


        autocomplete.setOnItemClickListener { parent, _, i, _ ->
            moduleViewModel.module.value = database.getModuleByFullName(parent.getItemAtPosition(i).toString())
        }

        add.setOnClickListener{
            val module = moduleViewModel.module.value
            // module = autocomplet
            val cap: Double = capselector.selectedItem as Double
            if(module != null) {
                database.modules.put(module, cap)

                val row = TableRow(activity)
                row.layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                )

                row.addView(formatTextView(TextView(activity), module.code));
                row.addView(formatTextView(TextView(activity), "$cap"));

                // table.addView(row)
            }
        }

        return root
    }

    fun formatTextView(view: TextView, text: String): TextView {
        view.layoutParams = TableRow.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                R.dimen.rowHeight
        ).apply {
            gravity = Gravity.CENTER
        }
        view.setPadding(padding, padding, padding, padding)
        view.text = text
        view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        // this.context?.let { text.setTextColor(ContextCompat.getColor(it, R.color.tableTextColor)) }
        view.textSize = resources.getDimensionPixelSize(R.dimen.table_text_size).toFloat()
        return view
    }
}