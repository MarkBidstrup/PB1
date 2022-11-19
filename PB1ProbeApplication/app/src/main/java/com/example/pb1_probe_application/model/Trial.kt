package com.example.pb1_probe_application.model

import com.google.firebase.firestore.DocumentId


data class Trial (
    @DocumentId val trialID: String = "",
    var researcherEmail: String = "",
    var title: String = "",
    var purpose: String = "",
    var briefDescription: String = "",
    var numParticipants: Int = 0,
    var registrationDeadline: String = "",
    var inclusionCriteria: String = "",
    var exclusionCriteria: String = "",
    var transportComp: Boolean = false,
    var compensation: Boolean = false,
    var lostSalaryComp: Boolean = false,
    var trialDuration: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var forsoegsBeskrivelse: String = "",
    var deltagerInformation: String = "",
    var locations: List<TrialLocation> = ArrayList(),
    var interventions: String = "",
    var diagnoses: List<String> = ArrayList()
    )

data class TrialLocation (
    var hospitalName: String = "",
    var address: String = "",
    var postCode: String = "",
    var city: String = ""
)

data class dbRegistrations(
    val participantEmail: String = "",
    val trialID: String = "")
