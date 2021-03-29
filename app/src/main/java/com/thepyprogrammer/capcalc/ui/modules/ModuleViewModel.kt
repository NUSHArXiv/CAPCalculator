package com.thepyprogrammer.capcalc.ui.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepyprogrammer.capcalc.model.data.CAPModule

class ModuleViewModel : ViewModel() {
    val moduleList = MutableLiveData<MutableList<CAPModule>>(mutableListOf())
}