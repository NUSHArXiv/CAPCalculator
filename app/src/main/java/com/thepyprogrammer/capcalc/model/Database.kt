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

    fun getNames(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(module in this) {
            if (module != null) arr.add(module.name)
        }
        return arr
    }

    fun getFullNames(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(module in this) {
            if (module != null) arr.add("%s (%s)".format(module.code, module.name))
        }
        return arr
    }
}