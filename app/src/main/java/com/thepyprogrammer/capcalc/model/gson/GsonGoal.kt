package com.thepyprogrammer.capcalc.model.gson

import com.google.gson.annotations.SerializedName
import com.thepyprogrammer.capcalc.model.data.Goal

data class GsonGoal(
    @SerializedName("title") var title: String,
    @SerializedName("isChecked") var isChecked: String = "false",
    @SerializedName("description") var description: String = ""
) {
    constructor(goal: Goal) : this(
        goal.title,
        "${goal.isChecked}",
        goal.description
    )

    fun toGoal() = Goal(
        title,
        isChecked.toBoolean(),
        description
    )
}