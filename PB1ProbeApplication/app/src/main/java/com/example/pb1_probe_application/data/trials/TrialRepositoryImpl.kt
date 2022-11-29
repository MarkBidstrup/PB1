package com.example.pb1_probe_application.data.trials

import com.example.pb1_probe_application.data.auth.AuthRepository
import com.example.pb1_probe_application.dataClasses.Trial
import com.example.pb1_probe_application.dataClasses.dbRegistrations
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrialRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: AuthRepository
) : TrialRepository {

    override val trials: Flow<List<Trial>>
        get() = trialDB().snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun getTrial(trialId: String): Trial? {
        return trialDB().document(trialId).get().await().toObject<Trial>()
    }

    override suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensationOffered: Boolean?
    ): List<Trial> {
        TODO("Not yet implemented")
//        val list: MutableList<Trial> = ArrayList()
//        val snapshot = trialDB().get().await()
//        snapshot.forEach { t -> list.add(t.toObject()) }
//        return list
    }

    override suspend fun addNew(trial: Trial) {
        trialDB().add(trial).await()
    }

    override suspend fun update(trial: Trial) {
        trialDB().document(trial.trialID).set(trial).await()
    }

    override suspend fun delete(trialId: String) {
        trialDB().document(trialId).delete().await()
        // TODO - also delete from subscribed list etc. Notify users first?
    }

    override suspend fun getMyTrialsParticipant(): List<Trial> {
        val email = auth.currentUser?.email
        val list: MutableList<Trial> = ArrayList()
        val snapshot = registrationDB().whereEqualTo("participantEmail", email).get().await()
        snapshot.forEach { t -> t.getString("trialID")?.let { id -> getTrial(id)?.let { list.add(it) } } }
        return list
    }

    override suspend fun getMyTrialsResearcher(): List<Trial> {
        val email = auth.currentUser?.email
        val list: MutableList<Trial> = ArrayList()
        val snapshot = trialDB().whereEqualTo("researcherEmail", email).get().await()
        snapshot.forEach { t -> list.add(t.toObject()) }
        return list
    }

    override suspend fun getSubscribedTrials(): List<Trial> {
        val email = auth.currentUser?.email
        val list: MutableList<Trial> = ArrayList()
        val snapshot = subscriptionDB().whereEqualTo("participantEmail", email).get().await()
        snapshot.forEach { t -> t.getString("trialID")?.let { id -> getTrial(id)?.let { list.add(it) } } }
        return list
    }

    override suspend fun registerForTrial(trialId: String) {
        val email = auth.currentUser?.email
        if(email != null)
            registrationDB().document(email + "_$trialId")
            .set(dbRegistrations(email, trialId)).await()
    }

    override suspend fun subscribeToTrial(trialId: String) {
        val email = auth.currentUser?.email
        if(email != null)
            subscriptionDB().document(email + "_$trialId")
            .set(dbRegistrations(email, trialId)).await()
    }

    override suspend fun unsubscribeFromTrial(trialId: String) {
        val email = auth.currentUser?.email
        if(email != null)
            subscriptionDB().document(email + "_$trialId").delete().await()
    }

    override suspend fun getRegisteredParticipants(trialId: String): List<String> {
        val emailList: MutableList<String> = ArrayList()
        val snapshot = registrationDB().whereEqualTo("trialID", trialId).get().await()
        snapshot.forEach { t -> t.getString("participantEmail")?.let { emailList.add(it) } }
        return emailList
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