package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.dataClasses.DropDownType
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilterScreen(trialsViewModel: TrialsViewModel, onClickNav: () -> Unit) {
    var locations by remember { mutableStateOf(String()) }
    var diagnoses by remember { mutableStateOf(String()) }
    var reward by remember { mutableStateOf(false) }
    var transport by remember { mutableStateOf(false) }
    var work by remember { mutableStateOf(false) }
    var maxDuration by remember { mutableStateOf<Int?>(null) }
    var maxVisits by remember { mutableStateOf<Int?>(null) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.filtrerStudier), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = onClickNav
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier
                .verticalScroll(scrollState)) {
                Spacer(modifier = Modifier.height(10.dp))
                DistanceComp() {
                    locations = it
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                CompensationComp(reward = reward, work = work, transport = transport, transportClick = {transport = !transport}, rewardClick = {reward = !reward}, workClick = {work = !work})
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                DurationComp { maxDuration = it}
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                VisitsComp{maxVisits = it}
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                DiagnosesComp() {
                    diagnoses = it
                }
                Row(
                    modifier = Modifier
                        .padding(17.dp)
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LoginButton(onClick = {
                        trialsViewModel.getFilteredTrials(
                            compensation = reward,
                            lostSalaryComp = work,
                            transportComp = transport,
                            location = locations,
                            diagnoses = diagnoses,
                            searchText = null,
                            trialDuration = maxDuration,
                            numVisits = maxVisits
                        )
                        if(reward || work || transport || locations != ""|| diagnoses != "" || maxVisits != null || maxDuration != null)
                            trialsViewModel.showFilterResult = true
                        onClickNav() }, R.string.filtrerKnap, true)
                }
            }
        },
        bottomBar = {

        }
    )
}

@Composable
fun DistanceComp(onDone: (String) -> Unit) {
    Column (modifier = Modifier.padding(17.dp)){
        Text(
            text = stringResource(R.string.lokationer),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.indtastKommune),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
    DropDownState(dropDownType = DropDownType.KOMMUNE, onDone, "")
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun CompensationComp(reward: Boolean, transport: Boolean, work: Boolean, rewardClick: (Boolean) -> Unit, transportClick: (Boolean) -> Unit, workClick: (Boolean) -> Unit ) {
    Column(modifier = Modifier
        .padding(all = 17.dp)) {
        Text(
            text = stringResource(R.string.kompensation),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Row {
            CheckOption(
                text = stringResource(R.string.honorar),
                checked = reward,
                onClick = rewardClick
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = stringResource(R.string.koersel),
                checked = transport,
                onClick = transportClick
            )
        }
        CheckOption(
            text = stringResource(R.string.arbejdsgodtgoersel),
            checked = work,
            onClick = workClick
        )
    }
}

@Composable
fun DurationComp(updateSelection: (Int?) -> Unit) {
    var time1 by remember { mutableStateOf(false) }
    var time2 by remember { mutableStateOf(false) }
    var time3 by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(all = 17.dp)) {
        Text(
            text = stringResource(R.string.varighed_Filter),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.maksVarighed),
            style = MaterialTheme.typography.body1,
        )
        Row {
            CheckOption(
                text = stringResource(R.string.maaned1),
                checked = time1,
                onClick = {
                    time1 = !time1
                    if (time1) {
                        time2 = false
                        time3 = false
                        updateSelection(1)
                    }
                    else
                        updateSelection(null)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = stringResource(R.string.maaned3),
                checked = time2,
                onClick = {
                    time2 = !time2
                    if (time2) {
                        time1 = false
                        time3 = false
                        updateSelection(3)
                    }
                    else
                        updateSelection(null)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = stringResource(R.string.maaned6),
                checked = time3,
                onClick = {
                    time3 = !time3
                    if (time3) {
                        time1 = false
                        time2 = false
                        updateSelection(6)
                    }
                    else
                        updateSelection(null)
                }
            )
        }
    }
}

@Composable
fun VisitsComp(updateSelection: (Int?) -> Unit) {
    var box1 by remember { mutableStateOf(false) }
    var box2 by remember { mutableStateOf(false) }
    var box3 by remember { mutableStateOf(false) }
    var box4 by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(all = 17.dp)) {
        Text(
            text = stringResource(R.string.antalBesoeg_Filter),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.maksantalBesoeg),
            style = MaterialTheme.typography.body1,
        )
        Row {
            CheckOption(
                text = "1",
                checked = box1,
                onClick = {
                    box1 = !box1
                    if (box1) {
                        box2 = false
                        box3 = false
                        box4 = false
                        updateSelection(1)
                    }
                    else
                        updateSelection(null)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = "3",
                checked = box2,
                onClick = {
                    box2 = !box2
                    if (box2) {
                        box1 = false
                        box3 = false
                        box4 = false
                        updateSelection(3)
                    }
                    else
                        updateSelection(null)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = "6",
                checked = box3,
                onClick = {
                    box3 = !box3
                    if (box3) {
                        box1 = false
                        box2 = false
                        box4 = false
                        updateSelection(6)
                    }
                    else
                        updateSelection(null)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = "10",
                checked = box4,
                onClick = {
                    box4 = !box4
                    if (box4) {
                        box1 = false
                        box2 = false
                        box3 = false
                        updateSelection(10)
                    }
                    else
                        updateSelection(null)
                }
            )
        }
    }
}

@Composable
fun CheckOption(
    text: String,
    checked: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Row( verticalAlignment = Alignment.CenterVertically ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Checkbox(checked = checked, onCheckedChange = onClick)
    }
}


@Composable
fun DiagnosesComp(onDone: (String) -> Unit) {
    Column (modifier = Modifier.padding(17.dp)){
        Text(
            text = stringResource(R.string.diagnose),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.vælgDiagnoseSymptomer),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
    DropDownState(dropDownType = DropDownType.DIAGNOSER, onDone, "")
    Spacer(modifier = Modifier.height(30.dp))
}



//@Preview
//@Composable
//fun FilterPreview() {
//    PB1ProbeApplicationTheme(darkTheme = false) {
//        FilterScreen(onClickNav = onClickNav)
//    }
//}
