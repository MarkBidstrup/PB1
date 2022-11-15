package com.example.pb1_probe_application.model.service

import com.example.pb1_probe_application.model.Trial
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class TrialServiceImpl : TrialService {
    private val firestore: FirebaseFirestore = Firebase.firestore

    override val trials: Flow<List<Trial>>
        get() = trialDB().snapshots().map { snapshot -> snapshot.toObjects() }

    override suspend fun getAllTrials(): List<Trial>? {
        val list: MutableList<Trial> = ArrayList()
        val snapshot = trialDB().get().await()
        snapshot.forEach { t -> list.add(t.toObject()) }
        return if(list.size == 0)
            null
        else
            list
    }

    override suspend fun getTrial(trialId: String): Trial? {
        return trialDB().document(trialId).get().await().toObject<Trial>()
    }

    override suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensationOffered: Boolean?
    ): List<Trial>? {
        TODO("Not yet implemented")
    }

    override suspend fun addNew(trial: Trial) {
        trialDB().add(trial).await()
    }

    override suspend fun update(trial: Trial) {
        trialDB().document(trial.trialID).set(trial).await()
    }

    override suspend fun delete(trialId: String) {
        trialDB().document(trialId).delete().await()
    }

    override suspend fun registerForTrial(trialId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getMyTrials(): List<Trial>? {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeToTrial(trialId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun unsubscribeFromTrial(trialId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getMySubscribedTrials(): List<Trial>? {
        TODO("Not yet implemented")
    }

    // TODO - test this
    override suspend fun getSubscribedParticipants(trialId: String): List<String>? {
        val ref = trialDB().document(trialId)
        val emailList: MutableList<String> = ArrayList()
        val snapshot = registrationDB().whereEqualTo("trialID", ref).get().await()
        snapshot.forEach { t -> t.getString("participantEmail")?.let { emailList.add(it) } }
        return if(emailList.size == 0)
            null
        else
            emailList
    }

    private fun trialDB(): CollectionReference =
        firestore.collection(TRIAL_COLLECTION)

    private fun registrationDB(): CollectionReference =
        firestore.collection(TRIAL_REGISTRATION)

    private fun subscriptionDB(): CollectionReference =
        firestore.collection(TRIAL_SUBSCRIPTION)

    companion object {
        private const val TRIAL_COLLECTION = "trials"
        private const val TRIAL_REGISTRATION = "trialRegistrations"
        private const val TRIAL_SUBSCRIPTION = "trialSubscriptions"
    }
}