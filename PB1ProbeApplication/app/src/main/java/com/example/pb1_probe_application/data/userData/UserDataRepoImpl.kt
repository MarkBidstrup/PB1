package com.example.pb1_probe_application.data.userData

import com.example.pb1_probe_application.data.auth.utils.await
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

    override suspend fun addNew(userID: String, data: UserData) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun update(userID: String, data: UserData) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun delete(userID: String) {
        userDataDB().document(userID).delete()
    }

    private fun userDataDB(): CollectionReference =
        firestore.collection("userInformation")
}