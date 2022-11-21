package com.example.pb1_probe_application.model

import com.google.firebase.firestore.DocumentId

data class UserPatient (
    @DocumentId val userID: String,
    var name: String,
    var lastName: String,
    var gender: String,
    var age: Int,
    var weight: Float,
    var diagnosis: String,
    var email: String,
    var phone: String,
)
