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
    private var myTrialsParticipants: List<Trial> = ArrayList()
    private var subscribedTrials: List<Trial> = ArrayList()
    private var myTrialsResearchers: List<Trial> = ArrayList()

    init {
        viewModelScope.launch {
            subscribedTrials = repository.getSubscribedTrials()
        }
    }

    fun getTrial(trialID: String): Trial? {
        var trial: Trial?
        runBlocking {
            trial = repository.getTrial(trialID)
        }
        return trial
    }

    fun getViewModelMyTrialsParticipants(): List<Trial> {
        runBlocking {
            myTrialsParticipants = repository.getMyTrialsParticipant()
        }
        return myTrialsParticipants
    }

    fun getViewModelMyTrialsResearchers(): List<Trial> {
        runBlocking {
            myTrialsResearchers = repository.getMyTrialsResearcher()
        }
        return myTrialsResearchers
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
        if(!myTrialsParticipants.contains(trial)) {
            val trials = myTrialsParticipants.toMutableList()
            trials.add(trial)
            myTrialsParticipants = trials
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
        if(!myTrialsResearchers.contains(trial)) {
            val trials = myTrialsResearchers.toMutableList()
            trials.add(trial)
            myTrialsResearchers = trials
        }
    }

    fun updateTrial(trial: Trial) {
        viewModelScope.launch {
            repository.update(trial)
        }
        var index = -1
        for (t in myTrialsResearchers) {
            if(t.trialID == trial.trialID)
                index = myTrialsResearchers.indexOf(t)
        }
        if(index > -1) {
            val trials = myTrialsResearchers.toMutableList()
            trials.removeAt(index)
            trials.add(index, trial)
            myTrialsResearchers = trials
        }
    }

    fun deleteTrial(trial: Trial) {
        viewModelScope.launch {
            repository.delete(trial.trialID)
        }
        if(myTrialsResearchers.contains(trial)) {
            val trials = myTrialsResearchers.toMutableList()
            trials.remove(trial)
            myTrialsResearchers = trials
        }
    }
}