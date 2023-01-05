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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateTrialScreen(id: String?, trialsViewModel: TrialsViewModel, onClickNavBack: () -> Unit, navMyTrials: () -> Unit) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.opretNytStudie), style = Typography.h1, ) },
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
            val trial = Trial()
            if(id != null)
                trial.researcherID = id
            Column(modifier = Modifier.padding(bottom = 0.dp)) {
                LazyColumn (
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)) {
                    val createTrialList = loadCreateTrialList()
                    items(createTrialList) {
                        CreateTrialField ->
                        var userInput by remember { mutableStateOf("") }
                        TrialInputField(
                            createTrialField = CreateTrialField,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            input = userInput,
                            onValueChange = {userInput = it}
                        )
                        if(userInput != "") {
                            when (CreateTrialField.trialAttribute) {
                                trialAttributes.title -> trial.title = userInput
                                trialAttributes.trialDuration -> trial.trialDuration = userInput
                                trialAttributes.numVisits -> trial.numVisits = Integer.parseInt(userInput)
                                trialAttributes.diagnoses -> trial.diagnoses = makeList(userInput)
                                trialAttributes.interventions -> trial.interventions = userInput
                                trialAttributes.startDate -> trial.startDate = userInput
                                trialAttributes.endDate -> trial.endDate = userInput
                                trialAttributes.lostSalaryComp -> trial.lostSalaryComp = StringToBool(userInput)
                                trialAttributes.transportComp -> trial.transportComp = StringToBool(userInput)
                                trialAttributes.locations -> trial.locations = userInput
                                trialAttributes.kommuner -> trial.kommuner = userInput
                                trialAttributes.compensation -> trial.compensation = StringToBool(userInput)
                                trialAttributes.exclusionCriteria -> trial.exclusionCriteria = userInput
                                trialAttributes.inclusionCriteria -> trial.inclusionCriteria = userInput
                                trialAttributes.numParticipants -> trial.numParticipants = Integer.parseInt(userInput)
                                trialAttributes.deltagerInformation -> trial.deltagerInformation = userInput
                                trialAttributes.forsoegsBeskrivelse -> trial.forsoegsBeskrivelse = userInput
                                trialAttributes.purpose -> trial.purpose = userInput
                                else -> {trial.briefDescription = userInput}
                            }
                        }
                        if (!(createTrialList.lastIndexOf(element = CreateTrialField) == createTrialList.lastIndex)) {
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
                    LoginButton(onClick = onClickNavBack, R.string.annuller, false)
                    LoginButton(onClick = {
                        if(trial.title == "")
                            Toast.makeText(context, R.string.indtastTitel, Toast.LENGTH_LONG).show()
                        else {
                            trialsViewModel.createNewTrial(trial)
                            navMyTrials()
                        }
                    }, R.string.bekræft, true)
                }
            }
        },
    )

}



@Composable
fun TrialInputField(
    createTrialField: CreateTrialField,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    input: String,
    onValueChange: (String) -> Unit) {

    Column {
        Text(
            text = LocalContext.current.getString(createTrialField.StringResourceHeading),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        if (
            LocalContext.current.getString(createTrialField.StringResourceHeading) == stringResource(id = R.string.transport)
            || LocalContext.current.getString(createTrialField.StringResourceHeading) == stringResource(id = R.string.tabtArbejdsfortjeneste)
        ) {
            DropDownState(DropDownType.JA_NEJ, onValueChange, input)
        } else if (
            LocalContext.current.getString(createTrialField.StringResourceHeading) == stringResource(id = R.string.antalDeltagere)
            || LocalContext.current.getString(createTrialField.StringResourceHeading) == stringResource(id = R.string.besoeg)
        ) {
            OutlinedTextField(
                value = input,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                onValueChange = onValueChange,
                textStyle = Typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions
            )
        } else if (
            LocalContext.current.getString(createTrialField.StringResourceHeading) == stringResource(id = R.string.kommune)
        ) {
            DropDownState(dropDownType = DropDownType.KOMMUNE, onValueChange, input)
        } else {
            OutlinedTextField(
                value = input,
                singleLine = true,
                label = { Text(text = LocalContext.current.getString(createTrialField.StringResourceFieldText)) },
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
}

@Preview
@Composable
private fun ProfileUserScreenPreview() {
//    EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
}

//Column {
//    Text(
//        text = LocalContext.current.getString(createTrialField.StringResourceHeading),
//        modifier = Modifier.padding(start = 17.dp),
//        style = MaterialTheme.typography.body1,
//        color = TextColorGreen
//    )
//    OutlinedTextField(
//        value = input,
//        singleLine = true,
//        label = { Text(text = LocalContext.current.getString(createTrialField.StringResourceFieldText)) },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 17.dp, end = 17.dp),
//        onValueChange = onValueChange,
//        textStyle = Typography.body1,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions
//    )
//}

