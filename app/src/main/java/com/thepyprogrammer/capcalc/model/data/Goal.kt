package com.thepyprogrammer.capcalc.model.data

data class Goal(
    var title: String,
    var isChecked: Boolean = false,
    var description: String = ""
) : Data
