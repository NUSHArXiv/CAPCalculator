package com.thepyprogrammer.capcalc.ui.goals.adapter

import android.app.AlertDialog
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.data.Goal
import com.thepyprogrammer.capcalc.ui.goals.GoalsFragment
import kotlinx.android.synthetic.main.item_goal.view.*


class GoalAdapter(
        private val parentFragment: GoalsFragment,
        val goals: MutableList<Goal> = mutableListOf()
) : RecyclerView.Adapter<GoalViewHolder>() {

    private var recentlyDeleted: Goal? = null
    private var recentlyDeletedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GoalViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_goal,
            parent,
            false
        ) as CardView
    )

    fun addGoal(goal: Goal) {
        goals.add(goal)
        notifyItemInserted(goals.size - 1)
        parentFragment.updateFile()
    }

    private fun toggleStrikeThrough(tvGoalTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvGoalTitle.paintFlags = tvGoalTitle.paintFlags or STRIKE_THRU_TEXT_FLAG

        } else {
            tvGoalTitle.paintFlags = tvGoalTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
        tvGoalTitle.isEnabled = !isChecked
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val curGoal = goals[position]
        holder.itemView.apply {
            goalTitleView.setText(curGoal.title)
            cbDone.isChecked = curGoal.isChecked
            toggleStrikeThrough(goalTitleView, curGoal.isChecked)


            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(goalTitleView, isChecked)
                curGoal.isChecked = !curGoal.isChecked
                parentFragment.updateFile()
            }

            goalTitleView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && goalTitleView.text.toString()
                            .isNotEmpty()
                    ) {
                        curGoal.title = goalTitleView.text.toString()
                        notifyDataSetChanged()
                        parentFragment.updateFile()
                        return true
                    }
                    return false
                }
            })

            holder.itemView.setOnClickListener {
                showExpandedGoalDialog(curGoal)

            }
        }
    }


    private fun showExpandedGoalDialog(goal: Goal) {
        val builder = AlertDialog.Builder(parentFragment.requireActivity())
        builder.setTitle("Edit Goal")
        // set the custom layout
        val customLayout: View =
            LayoutInflater.from(parentFragment.requireActivity().applicationContext).inflate(
                R.layout.expanded_item_goal,
                null
            )
        builder.setView(customLayout)
        // create and show the alert dialog

        val goalTitleView = customLayout.findViewById<EditText>(R.id.goalTitleView)
        val cbDone = customLayout.findViewById<CheckBox>(R.id.cbDone)
        val description = customLayout.findViewById<EditText>(R.id.detailsText)

        goalTitleView.setText(goal.title)
        cbDone.isChecked = goal.isChecked
        description.setText(goal.description)
        toggleStrikeThrough(goalTitleView, goal.isChecked)

        cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(goalTitleView, isChecked)
            goal.isChecked = !goal.isChecked
        }

        goalTitleView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER && goalTitleView.text.toString()
                        .isNotEmpty()
                ) {
                    goal.title = goalTitleView.text.toString()
                    return true
                }
                return false
            }
        })

        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            goal.description = description.text.toString()
            notifyDataSetChanged()
            parentFragment.updateFile()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun deleteItem(position: Int) {
        val goal = goals[position]
        goals.removeAt(position)
        notifyDataSetChanged()
        val view: View = parentFragment.requireActivity().findViewById(R.id.home)
        val snackbar: Snackbar = Snackbar.make(
            view, "Goal Has Been Deleted",
            Snackbar.LENGTH_LONG
        )
        recentlyDeleted = goal
        recentlyDeletedPosition = position
        snackbar.setAction("UNDO") { undoDelete() }
        snackbar.show()
        parentFragment.updateFile()
    }

    private fun undoDelete() {
        recentlyDeleted?.let {
            goals.add(
                recentlyDeletedPosition,
                it
            )
            notifyItemInserted(recentlyDeletedPosition)
            val view: View = parentFragment.requireActivity().findViewById(R.id.home)
            val snackbar: Snackbar = Snackbar.make(
                view, "Task Has Been Restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            parentFragment.updateFile()
        }
    }

    override fun getItemCount() = goals.size
}