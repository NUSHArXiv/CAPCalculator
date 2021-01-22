package com.thepyprogrammer.capcalc.model

import java.util.*

class Database internal constructor(moduleList: String) : ArrayList<Module?>() {
    init {
        val sc = Scanner(moduleList)
        while (sc.hasNext()) {
            val entry = sc.nextLine().split(",").toTypedArray()
            add(Module(entry[0].trim { it <= ' ' }, entry[1].trim { it <= ' ' }.toDouble(), entry[2].trim { it <= ' ' }, entry[3].trim { it <= ' ' }.toInt(), entry[4].trim { it <= ' ' }.toInt()))
        }
    }
}