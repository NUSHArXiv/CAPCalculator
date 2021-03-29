package com.thepyprogrammer.capcalc.ui.modules.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.data.CAPModule
import com.thepyprogrammer.capcalc.model.data.Module
import com.thepyprogrammer.capcalc.ui.modules.ModulesFragment
import kotlinx.android.synthetic.main.item_module.view.*

class ModuleAdapter(
        private val parentFragment: ModulesFragment,
        val modules: MutableList<CAPModule>
) : RecyclerView.Adapter<ModuleViewHolder>() {

    private var recentlyDeleted: CAPModule? = null
    private var recentlyDeletedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ModuleViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_module,
            parent,
            false
        ) as CardView
    )

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = modules[position]

        holder.itemView.apply {
            codeView.text = module.code
            nameView.text = module.name
            capView.text = module.cap.toString()
            when {
                module.cap <= 2.5 -> capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.fire_brick))
                module.cap >= 4.5 -> capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.green))
                else -> capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.SchoolBusYellow))
            }
        }
    }

    override fun getItemCount() = modules.size

    fun addModule(module: Module, cap: Double) {
        modules.forEachIndexed { index, it ->
            if(it.code == module.code) {
                it.cap = cap
                notifyItemChanged(index)
                parentFragment.updateFile()
                return@addModule
            }
        }
        modules.add(
            CAPModule(module.code, module.name, cap, module.mc, module.year, module.semester)
        )
        notifyItemInserted(modules.size - 1)
        sort()
        notifyDataSetChanged()
        parentFragment.updateFile()

    }

    private fun sort() {
        modules.sortWith { m1, m2 ->
            when {
                m1.year > m2.year -> 1
                m1.year < m2.year -> -1
                m1.semester > m2.semester -> 1
                m1.semester < m2.semester -> -1
                m1.code[3] > m2.code[3] -> 1
                m1.code[3] < m2.code[3] -> -1
                m1.code > m2.code -> 1
                m1.code < m2.code -> -1
                else -> 0
            }
        }
    }

    fun deleteItem(position: Int) {
        val module = modules[position]
        modules.removeAt(position)
        notifyDataSetChanged()
        val view: View = parentFragment.requireActivity().findViewById(R.id.home)
        val snackbar: Snackbar = Snackbar.make(
            view, "Module Has Been Deleted",
            Snackbar.LENGTH_LONG
        )
        recentlyDeleted = module
        recentlyDeletedPosition = position
        snackbar.setAction("UNDO") { undoDelete() }
        snackbar.show()
        parentFragment.updateFile()
    }

    private fun undoDelete() {
        recentlyDeleted?.let {
            modules.add(
                recentlyDeletedPosition,
                it
            )
            notifyItemInserted(recentlyDeletedPosition)
            val view: View = parentFragment.requireActivity().findViewById(R.id.home)
            val snackbar: Snackbar = Snackbar.make(
                view, "CAP Has Been Restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            parentFragment.updateFile()
        }
    }

}