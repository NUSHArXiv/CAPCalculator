package com.thepyprogrammer.capcalc.model

import com.thepyprogrammer.capcalc.R
import java.io.InputStream
import java.util.*


class Database internal constructor(moduleList: InputStream) : ArrayList<Module?>() {
    companion object {
        var currentOccurence: Database? = null
    }
    init {
        val sc = Scanner(moduleList)
        sc.nextLine()
        while (sc.hasNext()) {
            val entry = sc.nextLine().split(",").toTypedArray()
            add(Module(entry[0].trim { it <= ' ' }, entry[1].trim { it <= ' ' }.toDouble(), entry[2].trim { it <= ' ' }, entry[3].trim { it <= ' ' }.toInt(), entry[4].trim { it <= ' ' }.toInt()))
        }
        currentOccurence = this;
    }

    fun getCodes(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(module in this) {
            if (module != null) arr.add(module.code)
        }
        return arr
    }

    fun getNames(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(module in this) {
            if (module != null) arr.add(module.name)
        }
        return arr
    }

    fun getMCs(): ArrayList<Double> {
        val arr = ArrayList<Double>()
        for(module in this) {
            if (module != null) arr.add(module.mc)
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