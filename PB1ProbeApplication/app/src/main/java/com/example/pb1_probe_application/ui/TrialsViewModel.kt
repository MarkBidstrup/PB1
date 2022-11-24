package com.example.pb1_probe_application.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.trials.TrialRepository
import com.example.pb1_probe_application.model.Trial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel containing the app data and methods to process the data
 */

@HiltViewModel
class TrialsViewModel @Inject constructor(
    private val repository: TrialRepository
) : ViewModel(){
    val trials = repository.trials
    private val _trial = MutableStateFlow<Trial?>(null)
    val trial: StateFlow<Trial?> = _trial.asStateFlow()
    private val _myTrialsResearcher = MutableStateFlow<List<Trial>>(ArrayList())
    val myTrialsResearcher: StateFlow<List<Trial>> = _myTrialsResearcher.asStateFlow()
    private val _myTrialsParticipants = MutableStateFlow<List<Trial>>(ArrayList())
    val myTrialsParticipants: StateFlow<List<Trial>> = _myTrialsParticipants.asStateFlow()
    private val _subscribedTrials = MutableStateFlow<List<Trial>>(ArrayList())
    val subscribedTrials: StateFlow<List<Trial>> = _subscribedTrials.asStateFlow()
    private val _registeredParticipants = HashMap<String, MutableStateFlow<List<String>>>()


    fun getTrial(trialID: String)= viewModelScope.launch {
        val result = repository.getTrial(trialID)
        _trial.value = result
    }

    fun getViewModelMyTrialsParticipants() = viewModelScope.launch {
        val result = repository.getMyTrialsParticipant()
        _myTrialsParticipants.value = result
    }

    fun getViewModelMyTrialsResearchers() = viewModelScope.launch {
        val result = repository.getMyTrialsResearcher()
        _myTrialsResearcher.value = result
    }

    fun getViewModelSubscribedTrials() = viewModelScope.launch {
        val result = repository.getSubscribedTrials()
        _subscribedTrials.value = result
    }

    fun subscribeToTrial(trial: Trial)  = viewModelScope.launch {
        repository.subscribeToTrial(trial.trialID)
        getViewModelSubscribedTrials()
    }

    fun unsubscribeFromTrial(trial: Trial)  = viewModelScope.launch {
        repository.unsubscribeFromTrial(trial.trialID)
        getViewModelSubscribedTrials()
    }

    fun registerForTrial(trial: Trial)  = viewModelScope.launch {
        repository.registerForTrial(trial.trialID)
    }

    fun getViewModelRegisteredParticipants(trialID: String): StateFlow<List<String>> {
        if(!_registeredParticipants.containsKey(trialID)) {
            _registeredParticipants[trialID] = MutableStateFlow(ArrayList())
        }
        viewModelScope.launch {
            val result = repository.getRegisteredParticipants(trialID)
            _registeredParticipants[trialID]?.value = result
        }
        return _registeredParticipants[trialID]?.asStateFlow() ?: MutableStateFlow<List<String>>(ArrayList()).asStateFlow()
    }

    fun createNewTrial(trial: Trial) {
        viewModelScope.launch {
            repository.addNew(trial)
        }
        _registeredParticipants[trial.trialID] = MutableStateFlow(ArrayList())
        getViewModelMyTrialsResearchers()
    }

    fun updateTrial(trial: Trial) {
        viewModelScope.launch {
            repository.update(trial)
        }
        getViewModelMyTrialsResearchers()
    }

    fun deleteTrial(trial: Trial) {
        viewModelScope.launch {
            repository.delete(trial.trialID)
        }
        _registeredParticipants.remove(trial.trialID)
        getViewModelMyTrialsResearchers()
    }
}