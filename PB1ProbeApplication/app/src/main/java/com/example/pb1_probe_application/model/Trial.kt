package com.example.pb1_probe_application.model

import com.google.firebase.firestore.DocumentId


data class Trial (
    @DocumentId val trialID: String = "",
    val researcherEmail: String = "",
    val title: String = "",
    val purpose: String = "",
    val briefDescription: String = "",
    val numParticipants: Int = 0,
    val registrationDeadline: String = "",
    val inclusionCriteria: String = "",
    val exclusionCriteria: String = "",
    val transportComp: Boolean = false,
    val compensation: Boolean = false,
    val lostSalaryComp: Boolean = false,
    val trialDuration: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val forsoegsBeskrivelse: String = "",
    val deltagerInformation: String = "",
    var locations: List<TrialLocation> = ArrayList(),
    val interventions: String = "",
    val diagnoses: List<String> = ArrayList()
    )

data class TrialLocation (
    @DocumentId val hospitalName: String = "",
    val address: String = "",
    val postCode: String = "",
    val city: String = ""
)
