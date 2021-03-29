package com.thepyprogrammer.capcalc.model.calculations

import com.thepyprogrammer.capcalc.model.data.CAPModule

abstract class CAP(open var modules: MutableList<CAPModule> = mutableListOf()) {
    var cap = 0.0
    var above = 0.0
    var below = 0.0


    open fun calculate() {
        above = .0
        below = .0
        modules.forEach {
            below += it.mc
            above += it.mc * it.cap
        }
        if (below > 0) this.cap = above / below
    }

}