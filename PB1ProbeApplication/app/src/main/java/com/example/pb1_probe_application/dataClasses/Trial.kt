package com.example.pb1_probe_application.dataClasses

import com.example.pb1_probe_application.R
import com.google.firebase.firestore.DocumentId


data class Trial (
    @DocumentId val trialID: String = "",
    var researcherID: String = "",
    var title: String = "",
    var purpose: String = "",
    var numParticipants: Int = 0,
    var registrationDeadline: String = "",
    var inclusionCriteria: String = "",
    var exclusionCriteria: String = "",
    var transportComp: Boolean = false,
    var compensation: Boolean = false,
    var lostSalaryComp: Boolean = false,
    var trialDuration: String = "",
    var numVisits: Int = 0,
    var startDate: String = "",
    var endDate: String = "",
    var forsoegsBeskrivelse: String = "",
    var deltagerInformation: String = "",
    var locations: String = "",
    var interventions: String = "",
    var diagnoses: List<String> = ArrayList(),
    var kommuner: String = "" // used for database location search
    )

data class dbRegistrations(
    val participantID: String = "",
    val trialID: String = "")


enum class trialAttributes {
    title, purpose, numParticipants, registrationDeadline, inclusionCriteria,
    exclusionCriteria, transportComp, compensation, lostSalaryComp, trialDuration, numVisits, startDate, endDate,
    forsoegsBeskrivelse, deltagerInformation,  interventions, diagnoses, kommuner, locations
}

fun loadCreateTrialList(): List<CreateTrialField> {
    return listOf(
        CreateTrialField(trialAttributes.deltagerInformation, R.string.deltagerinformation, R.string.indtastDeltagerinfo),
        CreateTrialField(trialAttributes.title, R.string.titelPåStudie, R.string.indtastTitel),
        CreateTrialField(trialAttributes.purpose, R.string.formaal1, R.string.indtastformaal),
        CreateTrialField(trialAttributes.numParticipants, R.string.antalDeltagere, R.string.indtastDeltagere),
        CreateTrialField(trialAttributes.registrationDeadline, R.string.frist, R.string.indtastFrist),
        CreateTrialField(trialAttributes.locations, R.string.lokationer, R.string.indtastLokationer),
        CreateTrialField(trialAttributes.kommuner, R.string.kommune, R.string.indtastKommune),
        CreateTrialField(trialAttributes.trialDuration, R.string.projektetsVarighed, R.string.indtastVarighed),
        CreateTrialField(trialAttributes.numVisits, R.string.besoeg, R.string.indtastBesoeg),
        CreateTrialField(trialAttributes.endDate, R.string.forventetAfslutningsdato, R.string.indtastAfslutningsdato),
        CreateTrialField(trialAttributes.startDate, R.string.forventetstartdato, R.string.indtastforventetstartdato),
        CreateTrialField(trialAttributes.interventions, R.string.interventioner, R.string.indtastInterventioner),
        CreateTrialField(trialAttributes.diagnoses, R.string.diagnoseSymptomer, R.string.vælgDiagnoseSymptomer),
        CreateTrialField(trialAttributes.inclusionCriteria, R.string.inklKriterier, R.string.indtastInklKriterier),
        CreateTrialField(trialAttributes.exclusionCriteria, R.string.ekslKriterier, R.string.indtastEkslKriterier),
        CreateTrialField(trialAttributes.compensation, R.string.honorar, R.string.indtastHonorar),
        CreateTrialField(trialAttributes.transportComp, R.string.transport, R.string.tilbydesTransport),
        CreateTrialField(trialAttributes.lostSalaryComp, R.string.tabtArbejdsfortjeneste, R.string.tilbydesTabtArbejdsfortjeneste),
    )
}
