package com.thepyprogrammer.capcalc.model.data

data class CAPModule(
    var code: String,
    val name: String,
    var cap: Double,
    val mc: Double,
    val year: Int,
    val semester: Int
) : Data {

    fun isElective() = code[3] == '2'
}