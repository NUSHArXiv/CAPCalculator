package com.thepyprogrammer.capcalc.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.ui.home.HomeFragment
import kotlinx.android.synthetic.main.item_module.view.*

class HomeAdapter(
    private val parentFragment: HomeFragment,
    val caps: MutableList<Double>
) : RecyclerView.Adapter<CAPViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CAPViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_cap,
            parent,
            false
        ) as CardView
    )

    override fun onBindViewHolder(holder: CAPViewHolder, position: Int) {
        val cap = caps[position]
        
        holder.capView.text = String.format("%.1f", cap)
        when {
            cap <= 2.5 -> holder.capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.fire_brick))
            cap >= 4.5 -> holder.capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.green))
            else -> holder.capView.setTextColor(ContextCompat.getColor(parentFragment.requireActivity(), R.color.SchoolBusYellow))
        }

        if(position == 1) holder.nameView.text = "Most Recent Promo CAP"
    }

    override fun getItemCount() = 2

}