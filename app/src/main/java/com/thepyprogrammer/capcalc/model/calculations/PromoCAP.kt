package com.thepyprogrammer.capcalc.model.calculations

import com.thepyprogrammer.capcalc.model.data.CAPModule

class PromoCAP(
    override var modules: MutableList<CAPModule> = mutableListOf()
): CAP(modules) {

    private var mostRecentYear = 0
    private var mostRecentSem = 1

    init {
        modules.forEach {
            if(it.year > mostRecentYear) {
                mostRecentYear = it.year
                mostRecentSem = it.semester
            } else if(it.year == mostRecentYear && it.semester > mostRecentSem) {
                mostRecentSem = it.semester
            }
        }

        modules = modules.filter { it.year == mostRecentYear && if(mostRecentSem == 1) it.semester == mostRecentSem else true }.toMutableList()
        calculate()
    }
}