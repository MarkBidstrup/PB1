package com.example.pb1_probe_application.model

import androidx.annotation.StringRes

data class CreateTrialField(
    val trialAttribute: trialAttributes,
    @StringRes val StringResourceHeading: Int,
    @StringRes val StringResourceFieldText: Int
)
