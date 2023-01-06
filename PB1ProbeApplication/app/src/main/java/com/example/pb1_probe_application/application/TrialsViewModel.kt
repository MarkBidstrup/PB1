package com.example.pb1_probe_application.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.trials.TrialRepository
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.dataClasses.Trial
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
    private val repository: TrialRepository, private val userRepository: UserDataRepository
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
    private val _filteredTrials = MutableStateFlow<List<Trial>>(ArrayList())
    val filteredTrials: StateFlow<List<Trial>> = _filteredTrials.asStateFlow()
    private val _registeredParticipants = HashMap<String, MutableStateFlow<List<String>>>()
    private val _eligibleParticipants = HashMap<String, MutableStateFlow<Int>>()
    var currentNavTrial: Trial? = null // for navigation
        private set
    var showFilterResult: Boolean = false // for navigation


    fun getTrial(trialID: String)= viewModelScope.launch {
        val result = repository.getTrial(trialID)
        _trial.value = result
    }

    fun getFilteredTrials(searchText: String?, location: String? = null, diagnoses: String? = null, compensation: Boolean = false, transportComp: Boolean = false, lostSalaryComp: Boolean = false, trialDuration: Int? = null, numVisits: Int? = null)= viewModelScope.launch {
        val result = repository.getFilteredTrials(searchText, location, diagnoses, compensation, transportComp, lostSalaryComp, trialDuration, numVisits)
        _filteredTrials.value = result
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

    fun applyToTrial(trial: Trial)  = viewModelScope.launch {
        repository.registerForTrial(trial.trialID)
    }

    fun getRegisteredParticipantsUIDList(trialID: String): StateFlow<List<String>> {
        if(!_registeredParticipants.containsKey(trialID)) {
            _registeredParticipants[trialID] = MutableStateFlow(ArrayList())
        }
        viewModelScope.launch {
            val result = repository.getRegisteredParticipantsUID(trialID)
            _registeredParticipants[trialID]?.value = result
        }
        return _registeredParticipants[trialID]?.asStateFlow() ?: MutableStateFlow<List<String>>(ArrayList()).asStateFlow()
    }

    fun getTotalNumOfPotentialCandidates(trialID: String, diagnoses: List<String>): StateFlow<Int> {
        if(!_eligibleParticipants.containsKey(trialID)) {
            _eligibleParticipants[trialID] = MutableStateFlow(0)
        }
        viewModelScope.launch {
            val result = userRepository.getNumUsersWithCondition(diagnoses)
            _eligibleParticipants[trialID]?.value = result
        }
        return _eligibleParticipants[trialID]?.asStateFlow() ?: MutableStateFlow(0).asStateFlow()
    }


    fun createNewTrial(trial: Trial) {
        viewModelScope.launch {
            repository.addNew(trial)
        }
        _registeredParticipants[trial.trialID] = MutableStateFlow(ArrayList())
        _eligibleParticipants[trial.trialID] = MutableStateFlow(0)
    }

    fun updateTrial(trial: Trial) {
        viewModelScope.launch {
            repository.update(trial)
        }
    }

    fun deleteTrial(trial: Trial) {
        viewModelScope.launch {
            repository.delete(trial.trialID)
        }
        _registeredParticipants.remove(trial.trialID)
        _eligibleParticipants.remove(trial.trialID)
    }

    fun deleteCurrentUserFromAllTrialDBEntries() {
        viewModelScope.launch {
            repository.deleteUserFromAllTrialsDBs()
        }
    }

    fun setCurrentNavTrialID(trial: Trial) {
        currentNavTrial = trial
    }
}