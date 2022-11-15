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
        get() = currentCollection().snapshots().map { snapshot -> snapshot.toObjects() }

    override suspend fun getAllTrials(): List<Trial>? {
        val list: MutableList<Trial> = ArrayList()
        val snapshot = currentCollection().get().await()
        snapshot.forEach { t -> list.add(t.toObject()) }
        return if(list.size == 0)
            null
        else
            list
    }

    override suspend fun getTrial(trialId: String): Trial? {
        return currentCollection().document(trialId).get().await().toObject<Trial>()
    }

    override suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensationOffered: Boolean?
    ): List<Trial>? {
        TODO("Not yet implemented")
    }

    override suspend fun addNew(trial: Trial) {
        currentCollection().add(trial).await()
    }

    override suspend fun update(trial: Trial) {
        currentCollection().document(trial.trialID).set(trial).await()
    }

    override suspend fun delete(trialId: String) {
        currentCollection().document(trialId).delete().await()
    }

    private fun currentCollection(): CollectionReference =
        firestore.collection(TRIAL_COLLECTION)

    companion object {
        private const val TRIAL_COLLECTION = "trials"
    }
}