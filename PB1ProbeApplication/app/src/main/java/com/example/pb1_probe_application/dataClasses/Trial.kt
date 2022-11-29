package com.example.pb1_probe_application.dataClasses

import com.example.pb1_probe_application.R
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


enum class trialAttributes {
    title, purpose, briefDescription, numParticipants, registrationDeadline, inclusionCriteria,
    exclusionCriteria, transportComp, compensation, lostSalaryComp, trialDuration, startDate, endDate,
    forsoegsBeskrivelse, deltagerInformation, locations, interventions, diagnoses
}

fun loadCreateTrialList(): List<CreateTrialField> {
    return listOf<CreateTrialField>(
        CreateTrialField(trialAttributes.deltagerInformation, R.string.deltagerinformation, R.string.vedhæftFil),
        CreateTrialField(trialAttributes.title, R.string.titelPåStudie, R.string.indtastTitel),
        CreateTrialField(trialAttributes.purpose, R.string.formaal1, R.string.indtastformaal),
        CreateTrialField(trialAttributes.numParticipants, R.string.antalDeltagere, R.string.indtastDeltagere),
        CreateTrialField(trialAttributes.registrationDeadline, R.string.frist, R.string.indtastFrist),
        CreateTrialField(trialAttributes.briefDescription, R.string.kortBeskr, R.string.indtastKortBeskriv),
        CreateTrialField(trialAttributes.locations, R.string.lokationer, R.string.indtastLokationer),
        CreateTrialField(trialAttributes.trialDuration, R.string.projektetsVarighed, R.string.indtastVarighed),
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
