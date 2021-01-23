package com.thepyprogrammer.capcalc.ui.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thepyprogrammer.capcalc.R
import com.thepyprogrammer.capcalc.ui.home.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var moduleViewModel: ModuleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moduleViewModel =
            ViewModelProvider(this).get(ModuleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_modules, container, false)
        val textView: TextView = root.findViewById(R.id.text_modules)
        moduleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}