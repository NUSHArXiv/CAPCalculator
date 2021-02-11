package com.thepyprogrammer.capcalc.ui.goals

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thepyprogrammer.capcalc.R
import kotlinx.android.synthetic.main.item_goal.view.*

class GoalAdapter (
    private val goals: MutableList<Goal>
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        return GoalViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_goal,
                parent,
                false
            )
        )
    }

    fun addGoal(goal: Goal) {
        goals.add(goal)
        notifyItemInserted(goals.size - 1)
    }

    private fun toggleStrikeThrough(goalTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            goalTitle.paintFlags = goalTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            goalTitle.paintFlags = goalTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val curGoal = goals[position]
        holder.itemView.apply {
            goalTitle.text = curGoal.title
            cbox.isChecked = curGoal.isChecked
            toggleStrikeThrough(goalTitle, curGoal.isChecked)
            cbox.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(goalTitle, isChecked)
                curGoal.isChecked = !curGoal.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}