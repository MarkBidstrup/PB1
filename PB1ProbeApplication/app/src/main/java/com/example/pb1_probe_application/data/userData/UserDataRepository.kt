package com.example.pb1_probe_application.data.userData

import com.example.pb1_probe_application.dataClasses.UserData

interface UserDataRepository {

    suspend fun getData(userID: String): UserData
    suspend fun getData(userIDs: List<String>): List<UserData>

    suspend fun addNew(userID: String, data: UserData)
    suspend fun update(userID: String, data: UserData)

    suspend fun delete(userID: String)

    suspend fun getNumUsersWithCondition(diagnoses: List<String>): Int

    }