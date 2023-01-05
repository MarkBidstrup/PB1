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
        val id = auth.currentUser?.uid
        val list: MutableList<Trial> = ArrayList()
        val snapshot = registrationDB().whereEqualTo("participantID", id).get().await()
        snapshot.forEach { t -> t.getString("trialID")?.let { id -> getTrial(id)?.let { list.add(it) } } }
        return list
    }

    override suspend fun getMyTrialsResearcher(): List<Trial> {
        val id = auth.currentUser?.uid
        val list: MutableList<Trial> = ArrayList()
        val snapshot = trialDB().whereEqualTo("researcherID", id).get().await()
        snapshot.forEach { t -> list.add(t.toObject()) }
        return list
    }

    override suspend fun getSubscribedTrials(): List<Trial> {
        val id = auth.currentUser?.uid
        val list: MutableList<Trial> = ArrayList()
        val snapshot = subscriptionDB().whereEqualTo("participantID", id).get().await()
        snapshot.forEach { t -> t.getString("trialID")?.let { id -> getTrial(id)?.let { list.add(it) } } }
        return list
    }

    override suspend fun registerForTrial(trialId: String) {
        val id = auth.currentUser?.uid
        if(id != null)
            registrationDB().document(id + "_$trialId")
            .set(dbRegistrations(id, trialId)).await()
    }

    override suspend fun subscribeToTrial(trialId: String) {
        val id = auth.currentUser?.uid
        if(id != null)
            subscriptionDB().document(id + "_$trialId")
            .set(dbRegistrations(id, trialId)).await()
    }

    override suspend fun unsubscribeFromTrial(trialId: String) {
        val id = auth.currentUser?.uid
        if(id != null)
            subscriptionDB().document(id + "_$trialId").delete().await()
    }

    override suspend fun getRegisteredParticipantsUID(trialId: String): List<String> {
        val uidList: MutableList<String> = ArrayList()
        val emailList: MutableList<String> = ArrayList()
        val snapshot = registrationDB().whereEqualTo("trialID", trialId).get().await()
        snapshot.forEach { t -> t.getString("participantID")?.let { uidList.add(it) } }
        return emailList
    }

    override suspend fun getFilteredTrials(
        searchText: String?,
        location: String?,
        compensation: Boolean,
        transportComp: Boolean,
        lostSalaryComp: Boolean,
        trialDuration: Int?,
        numVisits: Int?
    ): List<Trial>{
        var list: MutableList<Trial> = ArrayList()

        if(searchText != null && searchText != "") { // if this is a search query
            // conducts case-sensitive prefix search of the title, purpose, and brief description
            // source: https://stackoverflow.com/questions/46568142/google-firestore-query-on-substring-of-a-property-value-text-search
            // answer by GabLeRoux on March 29, 2022
            val snapshot = trialDB()
                .whereGreaterThanOrEqualTo("title", searchText)
                .whereLessThanOrEqualTo("title", searchText + "\uf8ff")
                .get().await()
            snapshot.forEach { t -> list.add(t.toObject()) }
            val snapshot1 = trialDB()
                .whereGreaterThanOrEqualTo("purpose", searchText)
                .whereLessThanOrEqualTo("purpose", searchText + "\uf8ff")
                .get().await()
            snapshot1.forEach { t -> list.add(t.toObject()) }
            val snapshot2 = trialDB()
                .whereGreaterThanOrEqualTo("briefDescription", searchText)
                .whereLessThanOrEqualTo("briefDescription", searchText + "\uf8ff")
                .get().await()
            snapshot2.forEach { t -> list.add(t.toObject()) }
            val set = list.toSet() //remove duplicates
            list = set.toMutableList()

        } else { // if this is a filter query
            var locationsList: MutableList<Trial>? = null

            // get the list of trials filtered by location
            if(location != null) {
                locationsList = ArrayList()
                val snapshot = trialDB()
                    .whereEqualTo("kommuner", location)
                    .get().await()
                snapshot.forEach { t -> locationsList.add(t.toObject()) }
            }

            // get the list of trial filtered by various forms of compensation (using logical AND)
            // if any of the 3 compensation filters were applied
            var compList: MutableList<Trial> = ArrayList()
            var tempList: MutableList<Trial> = ArrayList()
            if (compensation || lostSalaryComp || transportComp) {
                if(compensation) {
                    val snapshot = trialDB()
                        .whereEqualTo("compensation", true)
                        .get().await()
                    snapshot.forEach { t -> compList.add(t.toObject()) }
                }
                if(transportComp) {
                    val snapshot = trialDB()
                        .whereEqualTo("transportComp", true)
                        .get().await()
                    snapshot.forEach { t -> tempList.add(t.toObject()) }
                    compList = if (compensation) {
                        // if both conditions were checked, we need the intersection
                        (compList intersect tempList.toSet()).toMutableList()
                    } else // only transportComp box was checked
                        tempList
                }
                if(lostSalaryComp) {
                    tempList = ArrayList()
                    val snapshot = trialDB()
                        .whereEqualTo("lostSalaryComp", true)
                        .get().await()
                    snapshot.forEach { t -> tempList.add(t.toObject()) }
                    compList = if (compensation || transportComp ) {
                        // if any of the other 2 conditions were checked, we need the intersection of the 2 lists
                        (compList intersect tempList.toSet()).toMutableList()
                    } else // only the lostSalaryComp box was checked
                        tempList
                }
                tempList = ArrayList()
            }

            if(trialDuration != null && trialDuration > 0) {
                for(i in 1 .. trialDuration) {
                    val month = if(i == 1)
                        " måned"
                    else
                        " måneder"
                    val snapshot = trialDB()
                        .whereEqualTo("trialDuration", "" + i + month)
                        .get().await()
                    snapshot.forEach { t -> tempList.add(t.toObject()) }
                }
            }
            if(numVisits != null && numVisits > 0) {
                val tempList2: MutableList<Trial> = ArrayList()
                for(i in 1 .. numVisits) {
                    val snapshot = trialDB()
                        .whereEqualTo("numVisits", i)
                        .get().await()
                    snapshot.forEach { t -> tempList2.add(t.toObject()) }
                }
                tempList = if(trialDuration != null && trialDuration > 0)
                    (tempList intersect tempList2.toSet()).toMutableList()
                else
                    tempList2
            }

            if (locationsList != null) {
                list = locationsList
            }
            if (compensation || lostSalaryComp || transportComp) {
                list = if (locationsList == null) {
                    compList
                } else
                    (list intersect compList.toSet()).toMutableList()
            }
            if((trialDuration != null && trialDuration > 0) || (numVisits != null && numVisits > 0)) {
                list = if(locationsList == null && !(compensation || lostSalaryComp || transportComp))
                    tempList
                else
                    (list intersect tempList.toSet()).toMutableList()
            }
        }
        val set = list.toSet() //remove duplicates
        list = set.toMutableList()
        return list
    }

    override suspend fun deleteUserFromAllTrialsDBs() {
        val id = auth.currentUser?.uid
        if(id != null) {
            val snapshot1 = registrationDB().whereEqualTo("participantID", id).get().await()
            snapshot1.forEach { t -> t.reference.delete().await()}
            val snapshot2 = subscriptionDB().whereEqualTo("participantID", id).get().await()
            snapshot2.forEach { t -> t.reference.delete().await()}
            val snapshot3 = trialDB().whereEqualTo("researcherID", id).get().await()
            snapshot3.forEach { t -> t.reference.delete().await()}
        }

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