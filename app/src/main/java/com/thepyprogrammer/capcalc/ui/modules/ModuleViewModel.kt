package com.thepyprogrammer.capcalc.ui.modules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepyprogrammer.capcalc.model.Module

class ModuleViewModel : ViewModel() {

    private val _module = MutableLiveData<Module>().apply {
        value = null
    }
    val module: LiveData<Module> = _module
}