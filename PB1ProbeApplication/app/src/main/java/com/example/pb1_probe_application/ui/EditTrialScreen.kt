package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.dataClasses.CreateTrialField
import com.example.pb1_probe_application.dataClasses.TrialLocation
import com.example.pb1_probe_application.dataClasses.loadCreateTrialList
import com.example.pb1_probe_application.dataClasses.trialAttributes
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditTrialScreen(trialsViewModel: TrialsViewModel, onClickNavBack: () -> Unit, navMyTrials: () -> Unit) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        topBar = {

            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.redigerStudie), style = Typography.h1, ) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = onClickNavBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.padding(bottom = 0.dp)) {
                val trial = trialsViewModel.currentNavTrial
                var edited by remember { mutableStateOf(false) }
                if (trial != null) {
                    LazyColumn (
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .weight(4f)) {
                        val createTrialList = loadCreateTrialList()
                        items(createTrialList) { EditTrialField ->
                            var input by remember { mutableStateOf("") }
                            when (EditTrialField.trialAttribute) {
                                trialAttributes.title -> input = trial.title
                                trialAttributes.trialDuration -> input = trial.trialDuration
                                trialAttributes.diagnoses -> input = trial.diagnoses.toString()
                                trialAttributes.interventions -> input = trial.interventions
                                trialAttributes.startDate -> input = trial.startDate
                                trialAttributes.endDate -> input = trial.endDate
                                trialAttributes.lostSalaryComp -> input = trial.lostSalaryComp.toString()
                                trialAttributes.transportComp -> input = trial.transportComp.toString()
                                trialAttributes.locations -> input = trial.locations.toString()
                                trialAttributes.compensation -> input = trial.compensation.toString()
                                trialAttributes.exclusionCriteria -> input = trial.exclusionCriteria
                                trialAttributes.inclusionCriteria -> input = trial.inclusionCriteria
                                trialAttributes.numParticipants -> input = trial.numParticipants.toString()
                                trialAttributes.deltagerInformation -> input = trial.deltagerInformation
                                trialAttributes.forsoegsBeskrivelse -> input = trial.forsoegsBeskrivelse
                                trialAttributes.purpose -> input = trial.purpose
                                else -> {input = trial.briefDescription }
                            }
                            var userInput by remember { mutableStateOf(input) }
                            EditTrialField(
                                createTrialField = EditTrialField,
                                input = userInput,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                onValueChange = {userInput = it}
                            )
                            if(userInput != input) {
                                edited = true
                                when (EditTrialField.trialAttribute) {
                                    trialAttributes.title -> trial.title = userInput
                                    trialAttributes.trialDuration -> trial.trialDuration = userInput
                                    trialAttributes.diagnoses -> trial.diagnoses = listOf(userInput)
                                    trialAttributes.interventions -> trial.interventions = userInput
                                    trialAttributes.startDate -> trial.startDate = userInput
                                    trialAttributes.endDate -> trial.endDate = userInput
                                    trialAttributes.lostSalaryComp -> trial.lostSalaryComp = userInput=="Ja"
                                    trialAttributes.transportComp -> trial.transportComp = userInput=="Ja"
                                    trialAttributes.locations -> trial.locations = listOf(TrialLocation(userInput))
                                    trialAttributes.compensation -> trial.compensation = userInput=="Ja"
                                    trialAttributes.exclusionCriteria -> trial.exclusionCriteria = userInput
                                    trialAttributes.inclusionCriteria -> trial.inclusionCriteria = userInput
                                    trialAttributes.numParticipants -> trial.numParticipants =
                                        if(userInput.toIntOrNull() != null)
                                            Integer.parseInt(userInput)
                                        else 0
                                    trialAttributes.deltagerInformation -> trial.deltagerInformation = userInput
                                    trialAttributes.forsoegsBeskrivelse -> trial.forsoegsBeskrivelse = userInput
                                    trialAttributes.purpose -> trial.purpose = userInput
                                    else -> {trial.briefDescription = userInput}
                                }
                            }
                            if (!(createTrialList.lastIndexOf(element = EditTrialField) == createTrialList.lastIndex)) {
                                Divider(
                                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp),
                                    thickness = 1.dp,
                                    color = androidx.compose.ui.graphics.Color.LightGray
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(.2f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        LoginButton(onClick = navMyTrials, R.string.annuller, false)
                        LoginButton(onClick = {
                            if(trial.title == "")
                                Toast.makeText(context, R.string.indtastTitel, Toast.LENGTH_LONG).show()
                            else {
                                if (edited)
                                    trialsViewModel.updateTrial(trial)
                                navMyTrials()
                            }
                        }, R.string.bekræft, true)
                    }
                }
            }
        },
    )

}



@Composable
fun EditTrialField(
    createTrialField: CreateTrialField,
    input: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit) {

    Column {
        Text(
            text = LocalContext.current.getString(createTrialField.StringResourceHeading),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        OutlinedTextField(
            value = input,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            onValueChange = onValueChange,
            textStyle = Typography.body1,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

//@Preview
//@Composable
//private fun ProfileUserScreenPreview() {
//    EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
//}