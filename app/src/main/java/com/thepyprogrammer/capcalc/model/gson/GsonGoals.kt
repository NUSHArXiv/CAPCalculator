package com.thepyprogrammer.capcalc.model.gson

import com.thepyprogrammer.capcalc.model.data.Goal
import java.util.*

class GsonGoals internal constructor(goals: MutableList<Goal>) : ArrayList<GsonGoal>() {
    init {
        goals.forEach {
            this.add(GsonGoal(it))
        }
    }
}