package com.thepyprogrammer.capcalc.model.calculations

import com.thepyprogrammer.capcalc.model.data.CAPModule

class GradCAP(
        override var modules: MutableList<CAPModule> = mutableListOf()
): CAP(modules) {

    private val preModules = mutableListOf<CAPModule>()
    private val electiveModules = mutableListOf<CAPModule>()
    private val actualModules = mutableListOf<CAPModule>()

    init {
        modules.forEach {
            when {
                it.year < 2 -> preModules.add(it)
                it.isElective() -> electiveModules.add(it)
                else -> actualModules.add(it)
            }
        }
        modules = actualModules
        calculate()
    }

    override fun calculate() {
        super.calculate()
        electiveModules.forEach {
            if (it.cap >= cap) {
                below += it.mc
                above += it.mc * it.cap
            }
        }
        if (below > 0) cap = above / below
    }
}