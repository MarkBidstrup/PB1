package com.example.pb1_probe_application.data.trials

import com.example.pb1_probe_application.dataClasses.Trial
import kotlinx.coroutines.flow.Flow

interface TrialRepository {
    val trials: Flow<List<Trial>>

    suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensation: Boolean,
        transportComp: Boolean,
        lostSalaryComp: Boolean,
        trialDuration: Int?,
        numVisits: Int?
    ): List<Trial>

    suspend fun getTrial(trialId: String): Trial?

    suspend fun addNew(trial: Trial)
    suspend fun update(trial: Trial)
    suspend fun delete(trialId: String)

    suspend fun getMyTrialsParticipant(): List<Trial>
    suspend fun getMyTrialsResearcher(): List<Trial>
    suspend fun getSubscribedTrials(): List<Trial>

    suspend fun registerForTrial(trialId: String)

    suspend fun subscribeToTrial(trialId: String)
    suspend fun unsubscribeFromTrial(trialId: String)

    suspend fun getRegisteredParticipants(trialId: String): List<String>

    suspend fun deleteUserFromAllTrialsDBs()


}