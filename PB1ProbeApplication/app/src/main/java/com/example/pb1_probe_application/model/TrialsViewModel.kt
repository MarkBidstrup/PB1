package com.example.pb1_probe_application.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.model.service.TrialServiceImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            myTrials = TrialServiceImpl().getMyTrials()
        }
    }

    fun getTrial(trialID: String): Trial? {
        var trial: Trial?
        runBlocking {
            trial = TrialServiceImpl().getTrial(trialID)
        }
        return trial
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

    fun registerForTrial(trial: Trial) {
        viewModelScope.launch {
            TrialServiceImpl().registerForTrial(trial.trialID)
        }
        if(!myTrials.contains(trial)) {
            val trials = myTrials.toMutableList()
            trials.add(trial)
            myTrials = trials
        }
    }

    fun getViewModelSubscribedParticipants(trialID: String): List<String> {
        var list: List<String>
        runBlocking {
            list = TrialServiceImpl().getSubscribedParticipants(trialID)
        }
        return list
    }

    fun createNewTrial(trial: Trial) {
        viewModelScope.launch {
            TrialServiceImpl().addNew(trial)
        }
    }

    fun updateTrial(trial: Trial) {
        viewModelScope.launch {
            TrialServiceImpl().update(trial)
        }
    }

    fun deleteTrial(trialID: String) {
        viewModelScope.launch {
            TrialServiceImpl().delete(trialID)
        }
    }
}