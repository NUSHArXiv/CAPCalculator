package com.thepyprogrammer.capcalc.model.gson

import com.google.gson.annotations.SerializedName
import com.thepyprogrammer.capcalc.model.data.CAPModule

data class GsonModule(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("cap") var cap: String,
    @SerializedName("mc") var mc: String,
    @SerializedName("year") val year: String,
    @SerializedName("semester") val semester: String
) {
    constructor(module: CAPModule) : this(
        module.code,
        module.name,
        "${module.cap}",
        "${module.mc}",
        "${module.year}",
        "${module.semester}"
    )

    fun toModule() = CAPModule(
        code,
        name,
        cap.toDouble(),
        mc.toDouble(),
        year.toInt(),
        semester.toInt()

    )
}