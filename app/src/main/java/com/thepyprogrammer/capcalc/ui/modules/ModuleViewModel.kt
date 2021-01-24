package com.thepyprogrammer.capcalc.ui.modules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepyprogrammer.capcalc.model.Module

class ModuleViewModel : ViewModel() {

    val module = MutableLiveData<Module>().apply {
        value = null
    }
}