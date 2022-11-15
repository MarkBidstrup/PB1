package com.example.pb1_probe_application.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.service.TrialService
import com.example.pb1_probe_application.model.service.TrialServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel containing the app data and methods to process the data
 */

class TrialsViewModel : ViewModel(){
    var trials: MutableList<Trial> = ArrayList()
    fun getViewModelTrials(): List<Trial> {
        viewModelScope.launch {
            trials = TrialServiceImpl().getAllTrials() as MutableList<Trial>
        }
        return trials
    }
}