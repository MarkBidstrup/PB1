package com.example.pb1_probe_application.model.service

import com.example.pb1_probe_application.model.Trial
import com.example.pb1_probe_application.model.UserPatient
import kotlinx.coroutines.flow.Flow

interface TrialService {
    val trials: Flow<List<Trial>>

    // TODO - change parameters depending on filtering implementation
    suspend fun getFilteredTrials(searchText: String?, location: String?, compensationOffered: Boolean?): List<Trial>?

    suspend fun getTrial(trialId: String): Trial?

    suspend fun addNew(trial: Trial)
    suspend fun update(trial: Trial)
    suspend fun delete(trialId: String)

    suspend fun getMyTrials(): List<Trial>
    suspend fun getSubscribedTrials(): List<Trial>

    suspend fun registerForTrial(trialId: String)

    suspend fun subscribeToTrial(trialId: String)
    suspend fun unsubscribeFromTrial(trialId: String)

    suspend fun getSubscribedParticipants(trialId: String): List<String>?

}