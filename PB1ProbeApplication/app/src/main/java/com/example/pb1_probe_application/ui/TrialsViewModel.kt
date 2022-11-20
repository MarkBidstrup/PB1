package com.example.pb1_probe_application.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.trials.TrialRepository
import com.example.pb1_probe_application.model.Trial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * ViewModel containing the app data and methods to process the data
 */

@HiltViewModel
class TrialsViewModel @Inject constructor(
    private val repository: TrialRepository
) : ViewModel(){
    val trials = repository.trials
    private var myTrials: List<Trial> = ArrayList()
    private var subscribedTrials: List<Trial> = ArrayList()

    init {
        viewModelScope.launch {
            subscribedTrials = repository.getSubscribedTrials()
            myTrials = repository.getMyTrials()
        }
    }

    fun getTrial(trialID: String): Trial? {
        var trial: Trial?
        runBlocking {
            trial = repository.getTrial(trialID)
        }
        return trial
    }

    fun getViewModelMyTrials(): List<Trial> {
        viewModelScope.launch {
            myTrials = repository.getMyTrials()
        }
        return myTrials
    }

    fun getViewModelSubscribedTrials(): List<Trial> {
        viewModelScope.launch {
            subscribedTrials = repository.getSubscribedTrials()
        }
        return subscribedTrials
    }

    fun subscribeToTrial(trial: Trial) {
        viewModelScope.launch {
            repository.subscribeToTrial(trial.trialID)
        }
        if(!subscribedTrials.contains(trial)) {
            val trials = subscribedTrials.toMutableList()
            trials.add(trial)
            subscribedTrials = trials
        }
    }

    fun unsubscribeFromTrial(trial: Trial) {
        viewModelScope.launch {
            repository.unsubscribeFromTrial(trial.trialID)
        }
        val trials = subscribedTrials.toMutableList()
        trials.remove(trial)
        subscribedTrials = trials
    }

    fun registerForTrial(trial: Trial) {
        viewModelScope.launch {
            repository.registerForTrial(trial.trialID)
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
            list = repository.getSubscribedParticipants(trialID)
        }
        return list
    }

    fun createNewTrial(trial: Trial) {
        viewModelScope.launch {
            repository.addNew(trial)
        }
    }

    fun updateTrial(trial: Trial) {
        viewModelScope.launch {
            repository.update(trial)
        }
    }

    fun deleteTrial(trialID: String) {
        viewModelScope.launch {
            repository.delete(trialID)
        }
    }
}