package com.example.pb1_probe_application.dataClasses

enum class Diagnoses(val diagnosis: String) {
    HEALTHY("Rask / ingen diagnoser"),
    HIGH_BMI("Overvægtig (BMI > 25)"),
    DIABETES_I("Diabetes type 1"),
    DIABETES_II("Diabetes type 2"),
    PAINS("Smerter"),
    ALZHEIMER("Alzheimer"),
    HEART("Hjertesygdomme"),
    CHILDLESS("Ufrivillig barnløshed"),
    DEPRESSION("Depression"),
    MENTAL("Psykiske sygdomme"),
    CANCER("Kræftsygdomme"),
    REHABILITATION("Genoptræning / rehabilitering"),
    NUTRITION("Kost og ernæring"),
    AGING("Aldring"),
    CHILDREN("Børn"),
    OTHER("Andet")
}