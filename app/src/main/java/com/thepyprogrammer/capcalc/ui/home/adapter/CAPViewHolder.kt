package com.thepyprogrammer.capcalc.ui.home.adapter

import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.data.CAPModule

class CAPViewHolder(view: CardView) : RecyclerView.ViewHolder(view) {
    val nameView: TextView = view.findViewById(R.id.nameView)
    val capView: TextView = view.findViewById(R.id.capView)
    var module: CAPModule? = null
}