package com.thepyprogrammer.capcalc.ui.goals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.model.data.Goal
import com.thepyprogrammer.capcalc.model.gson.GsonGoal
import com.thepyprogrammer.capcalc.model.gson.GsonGoals
import com.thepyprogrammer.capcalc.ui.goals.adapter.GoalAdapter
import com.thepyprogrammer.capcalc.ui.goals.adapter.GoalSwipeToDeleteCallback
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*

class GoalsFragment : Fragment() {

    private lateinit var goalAdapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_goals, container, false)
        goalAdapter = GoalAdapter(this, readFile())

        val rvGoals = root.findViewById<RecyclerView>(R.id.rvGoalItems)
        rvGoals.adapter = goalAdapter
        rvGoals.layoutManager = LinearLayoutManager(activity)
        val itemTouchHelper = ItemTouchHelper(GoalSwipeToDeleteCallback(goalAdapter))
        itemTouchHelper.attachToRecyclerView(rvGoals)
        rvGoals.layoutManager = LinearLayoutManager(root.context)

        val addGoal = root.findViewById<Button>(R.id.addGoal)

        val goalTitleText = root.findViewById<EditText>(R.id.goalTitleText)
        val goalDetailsText = root.findViewById<EditText>(R.id.goalDetailsText)

        addGoal.setOnClickListener {
            val goalTitle = goalTitleText.text.toString()
            if (goalTitle.isNotEmpty()) {
                val goal = Goal(goalTitle, description = goalDetailsText.text.toString())
                goalAdapter.addGoal(goal)
                goalTitleText.text.clear()
                goalDetailsText.text.clear()
            }
        }

        return root
    }

    private fun readFile(): MutableList<Goal> {
        try {
            val dbFile = File(requireActivity().filesDir.path.toString() + "/goals.json")
            if (!dbFile.exists()) dbFile.createNewFile()
            val sc = Scanner(dbFile)
            var s = ""
            val gson = Gson()
            while (sc.hasNext()) {
                s += sc.nextLine()
            }
            sc.close()
            val sType = object : TypeToken<List<GsonGoal>>() {}.type
            val list = mutableListOf<Goal>()
            gson.fromJson<List<GsonGoal>>(s, sType)?.forEach {
                list.add(it.toGoal())
            }
            return list
        } catch (ex: IOException) {
            Log.i("ERR", ex.stackTrace.toString())
            return mutableListOf()
        }
    }

    fun updateFile() {
        try {
            val gGoals = GsonGoals(goalAdapter.goals)
            val gson = Gson()
            val jsonString = gson.toJson(gGoals)
            val dbFile = File(requireActivity().filesDir.path.toString() + "/goals.json")
            if (!dbFile.exists()) dbFile.createNewFile()
            val pw = PrintWriter(dbFile)
            pw.println(jsonString)
            pw.close()
        } catch (ex: IOException) {
            Log.i("ERR", ex.stackTrace.toString())
        }
    }

    override fun onDestroy() {
        updateFile()
        super.onDestroy()
    }
}