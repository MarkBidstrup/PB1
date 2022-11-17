package com.example.pb1_probe_application.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.model.service.TrialServiceImpl
import kotlinx.coroutines.launch

/**
 * ViewModel containing the app data and methods to process the data
 */

class TrialsViewModel : ViewModel(){
    val trials = TrialServiceImpl().trials
    private var myTrials: List<Trial> = ArrayList()
    private var subscribedTrials: List<Trial> = ArrayList()

    init {
        viewModelScope.launch {
            subscribedTrials = TrialServiceImpl().getSubscribedTrials()
//            myTrials = TrialServiceImpl().getMyTrials()
        }
    }

    fun getViewModelMyTrials(): List<Trial> {
        viewModelScope.launch {
            myTrials = TrialServiceImpl().getMyTrials()
        }
        return myTrials
    }

    fun getViewModelSubscribedTrials(): List<Trial> {
        viewModelScope.launch {
            subscribedTrials = TrialServiceImpl().getSubscribedTrials()
        }
        return subscribedTrials
    }

    fun subscribeToTrial(trial: Trial) {
        viewModelScope.launch {
            TrialServiceImpl().subscribeToTrial(trial.trialID)
        }
        if(!subscribedTrials.contains(trial)) {
            val trials = subscribedTrials.toMutableList()
            trials.add(trial)
            subscribedTrials = trials
        }
    }

    fun unsubscribeFromTrial(trial: Trial) {
        viewModelScope.launch {
            TrialServiceImpl().unsubscribeFromTrial(trial.trialID)
        }
        val trials = subscribedTrials.toMutableList()
        trials.remove(trial)
        subscribedTrials = trials
    }

}