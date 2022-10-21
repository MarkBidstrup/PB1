package com.example.pb1_probe_application.model

import java.util.*

data class TrialState (
    // TODO add deltagerinformation
    val trialName: String,
    val goal: String,
    val registrationDeadline: Date,
    val numParticipantsRequired: Int,
    val inclusionCriteria: String,
    val exclusionCriteria: String,
    val compensation: Int,
    val locations: String,
    val transportComp: Boolean,
    val lostSalaryComp: Boolean,
    val trialDuration: String,
    val numVisits: Int,
    val lengthOfEachVisit: String,
    val expectedEndDate: Date,
    val expectedReplyDate: Date,
    val diagnoses: List<String>,

    val numParticipantsRegistered: Int,     // TODO this needs to be a list of registered participants?
    val numPotentialParticipants: Int,
    val researcherName: String,
    val researcherInst: String,
    val researcherPhone: String
)