package com.thepyprogrammer.capcalc.model.gson

import com.thepyprogrammer.capcalc.model.data.CAPModule
import java.util.*

class GsonModules internal constructor(modules: MutableList<CAPModule>) : ArrayList<GsonModule>() {
    init {
        modules.forEach {
            this.add(GsonModule(it))
        }
    }
}