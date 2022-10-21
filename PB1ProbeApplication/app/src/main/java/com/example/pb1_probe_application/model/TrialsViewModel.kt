package com.example.pb1_probe_application.model

import androidx.lifecycle.ViewModel
import com.example.pb1_probe_application.data.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel containing the app data and methods to process the data
 */

class TrialsViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(Datasource().loadTrials())
    val uiState: StateFlow<List<TrialState>> = _uiState.asStateFlow()
}