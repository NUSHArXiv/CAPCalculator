package com.thepyprogrammer.capcalc.model

import kotlin.collections.HashMap

class CAP() {
    var modules = HashMap<Module, Double>();
    var cap = 0.0;

    constructor(modules: HashMap<Module, Double>) : this() {
        this.modules = modules;
        var above = 0.0
        var below = 0.0
        modules.forEach() {
            below += it.key.mc
            above += it.key.mc * it.value
        }
        if(above > 0) this.cap = above / below


    }

}