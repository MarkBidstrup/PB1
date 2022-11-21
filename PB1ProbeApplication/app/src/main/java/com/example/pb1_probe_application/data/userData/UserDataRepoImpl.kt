package com.example.pb1_probe_application.data.userData

import com.example.pb1_probe_application.data.auth.utils.await
import com.example.pb1_probe_application.model.UserPatient
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore, // TODO add private val accountService
) : UserDataRepository {

    override val userData: Flow<List<UserPatient>>
        get() = userDataDB().snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun getData(userID: String): UserPatient {
        val userData = UserPatient()
        val data = userDataDB().document(userID).get()

        userData.name = data.result.get("name") as String
        userData.lastName = data.result.get("lastName") as String
        userData.gender = data.result.get("gender") as String
        userData.age = data.result.get("age") as Int
        userData.weight = data.result.get("weight") as Double
        userData.diagnosis = data.result.get("diagnosis") as String
        userData.email = data.result.get("email") as String
        userData.phone = data.result.get("phone") as String

        return userData
        //return userDataDB().document(userID).get().await().toObject<UserPatient>()
    }

    override suspend fun addNew(userID: String, data: UserPatient) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun update(userID: String, data: UserPatient) {
        userDataDB().document(userID).set(data).await()
    }

    override suspend fun delete(userID: String) {
        userDataDB().document(userID).delete()
    }


    private fun userDataDB(): CollectionReference =
        firestore.collection("userInformation")

}