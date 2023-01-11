package com.example.pb1_probe_application.data.userData

import com.example.pb1_probe_application.data.auth.utils.await
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.UserData
import com.example.pb1_probe_application.dataClasses.UserPatient
import com.example.pb1_probe_application.dataClasses.UserResearcher
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class UserDataRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserDataRepository {

    override suspend fun getData(userID: String): UserData {
        val data = userDataDB().document(userID).get().await()
        if (data.toObject<UserPatient>() == null) {
            return UserPatient()
        }
        val userData: UserData = if (data.getString("role") == "TRIAL_PARTICIPANT") {
            data.toObject<UserPatient>()!!
        } else {
            data.toObject<UserResearcher>()!!
        }
        return userData
    }

    override suspend fun getData(userIDs: List<String>): List<UserData> {
        val dataList = mutableListOf<UserData>()
        for (uid in userIDs) {
            val data = userDataDB().document(uid).get().await()
            if (data.toObject<UserPatient>() != null) {
                dataList.add(
                    if (data.getString("role") == "TRIAL_PARTICIPANT") {
                        data.toObject<UserPatient>()!!
                    } else {
                        data.toObject<UserResearcher>()!!
                    }
                )
            }
        }
        return dataList
    }

            override suspend fun addNew(userID: String, data: UserData) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun update(userID: String, data: UserData) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun delete(userID: String) {
        userDataDB().document(userID).delete()
    }

    // gets the number of trial participants who have at least one of the conditions/ diagnoses
    override suspend fun getNumUsersWithCondition(diagnoses: List<String>): Int {
        val eligibleList: MutableList<String> = ArrayList()
        return if(diagnoses.isNotEmpty() && diagnoses[0] != "") {
            for(d in diagnoses) {
                val snapshot = userDataDB()
                    .whereArrayContains("diagnosis", d)
                    .get().await()
                snapshot.forEach { t -> eligibleList.add(t.id) }
            }
            val set = eligibleList.toSet() //remove duplicates
            set.size
        } else { // if there are no conditions/ listed diagnoses, all trial participants are eligible
            val snapshot1 = userDataDB().whereEqualTo("role", Role.TRIAL_PARTICIPANT.name).get().await()
            snapshot1.forEach { t -> eligibleList.add(t.id)}
            eligibleList.size
        }
    }

    private fun userDataDB(): CollectionReference =
        firestore.collection("userInformation")
}