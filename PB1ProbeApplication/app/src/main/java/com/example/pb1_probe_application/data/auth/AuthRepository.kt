package com.example.pb1_probe_application.data.auth

import com.google.firebase.auth.FirebaseUser

// code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    suspend fun forgotPassword(email: String): Resource<Boolean>
    fun logout()
    fun delete()
    suspend fun updateEmail(email: String): Resource<Boolean>
}