package com.thepyprogrammer.capcalc.model

data class Module(val code:String, val mc:Double, val name:String, val year:Int, val semester:Int) {
    override fun toString(): String {
        return "$code $name (mc=$mc)"
    }
}