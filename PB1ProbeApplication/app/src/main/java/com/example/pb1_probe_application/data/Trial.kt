/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pb1_probe_application.data

import androidx.annotation.StringRes
import com.example.pb1_probe_application.R

/**
 * A data class to represent the information presented in the dog card
 */
data class Trial(
    @StringRes var titel: Int,
    @StringRes var formaal: Int,
    @StringRes var tilmeldingsfrist: Int,
    @StringRes var lokation: Int,
    @StringRes var forloeb: Int,
    @StringRes var varighed: Int,
    @StringRes var sygdom: Int,
    @StringRes var intervention: Int,


)

val trials = listOf(
    Trial (
        R.string.titel_1,
        R.string.formaal_1,
        R.string.tilmeldingsfrist_1,
        R.string.lokation_1,
        R.string.forloeb_1,
        R.string.varighed_1,
        R.string.sygdom_1,
        R.string.intervention_1,
    ),
    Trial (
        R.string.titel_1,
        R.string.formaal_1,
        R.string.tilmeldingsfrist_1,
        R.string.lokation_1,
        R.string.forloeb_1,
        R.string.varighed_1,
        R.string.sygdom_1,
        R.string.intervention_1,
    ),
    Trial (
        R.string.titel_1,
        R.string.formaal_1,
        R.string.tilmeldingsfrist_1,
        R.string.lokation_1,
        R.string.forloeb_1,
        R.string.varighed_1,
        R.string.sygdom_1,
        R.string.intervention_1,
    ),
    Trial (
        R.string.titel_1,
        R.string.formaal_1,
        R.string.tilmeldingsfrist_1,
        R.string.lokation_1,
        R.string.forloeb_1,
        R.string.varighed_1,
        R.string.sygdom_1,
        R.string.intervention_1,
    )
)