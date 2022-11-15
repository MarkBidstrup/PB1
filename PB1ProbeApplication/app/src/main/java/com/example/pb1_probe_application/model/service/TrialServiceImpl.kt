package com.example.pb1_probe_application.model.service

import androidx.compose.animation.core.snap
import com.example.pb1_probe_application.model.Trial
import com.example.pb1_probe_application.model.TrialLocation
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
        snapshot.forEach { t ->
            val trial = t.toObject<Trial>()
            val locs = currentCollection().document(t.id).collection("locations").get().await()
            val locations = locs.documents.map { it.toObject<TrialLocation>() }
            if (locations[0] != null)
                trial.locations = locations as List<TrialLocation>
            list.add(trial)
        }
        return if(list.size == 0)
            null
        else
            list
    }

    override suspend fun getTrial(trialId: String): Trial? {
        val trial = currentCollection().document(trialId).get().await().toObject<Trial>()
        val snapshot = currentCollection().document(trialId).collection("locations").get().await()
        val locations = snapshot.documents.map { it.toObject<TrialLocation>() }
        if (trial != null && locations[0] != null)
            trial.locations = locations as List<TrialLocation>
        return trial
    }

    override suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensationOffered: Boolean?
    ): List<Trial>? {
        TODO("Not yet implemented")
    }

    // TODO - make sure that nested objects (locations) get added correctly
    override suspend fun addNew(trial: Trial) {
        currentCollection().add(trial)
    }

    override suspend fun update(trial: Trial) {
        currentCollection().document(trial.trialID).set(trial)
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