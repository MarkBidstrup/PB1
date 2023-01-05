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
    private val firestore: FirebaseFirestore, // TODO add private val accountService
) : UserDataRepository {

//    override val userData: Flow<List<UserPatient>>
//        get() = userDataDB().snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun getData(userID: String): UserData {

        val data = userDataDB().document(userID).get().await()
        var userData: UserData

        if (data.getString("role") == "TRIAL_PARTICIPANT") {
            userData = data.toObject<UserPatient>()!!
            //userData = UserPatient()
            /*userData.name = data.result.get("name") as String
            userData.lastName = data.result.get("lastName") as String
            userData.email = data.result.get("email") as String
            userData.phone = data.result.get("phone") as String
            userData.gender = data.result.get("gender") as String
            userData.age = data.result.get("age") as String
            userData.weight = data.result.get("weight") as String
            userData.diagnosis = data.result.get("diagnosis") as String*/
        } else {
            userData = data.toObject<UserResearcher>()!!
            /*userData = UserResearcher()
            userData.name = data.result.get("name") as String
            userData.lastName = data.result.get("lastName") as String
            userData.email = data.result.get("email") as String
            userData.phone = data.result.get("phone") as String
            userData.department = data.result.get("department") as String
            userData.job = data.result.get("job") as String*/
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