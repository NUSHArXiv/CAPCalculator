package com.thepyprogrammer.capcalc.ui.goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepyprogrammer.capcalc.R
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : Fragment() {

    private lateinit var goalAdapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_goals, container, false)
        goalAdapter = GoalAdapter(mutableListOf())

        //val rvGoals = root.findViewById<RecyclerView>(R.id.rvGoals)
        rvGoals.adapter = goalAdapter
        rvGoals.layoutManager = LinearLayoutManager(root.context)

        //val addGoal = root.findViewById<Button>(R.id.addGoal)

        addGoal.setOnClickListener {
            val goalTitle = etGoalTitle.text.toString()
            if(goalTitle.isNotEmpty()) {
                val goal = Goal(goalTitle)
                goalAdapter.addGoal(goal)
                etGoalTitle.text.clear()
            }
        }





        return root
    }
}