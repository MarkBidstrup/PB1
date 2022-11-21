package com.example.pb1_probe_application.data.userData

import com.example.pb1_probe_application.model.UserPatient
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<List<UserPatient>>

    suspend fun getData(userID: String): UserPatient

    suspend fun addNew(userID: String, data: UserPatient)
    suspend fun update(userID: String, data: UserPatient)
    suspend fun delete(userID: String)

}