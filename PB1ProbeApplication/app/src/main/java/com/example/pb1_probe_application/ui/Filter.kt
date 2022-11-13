package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun FilterScreen() {
    Scaffold(
        topBar = {

            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.filtrerStudier), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                DistanceComp()
                Divider(
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp, bottom = 10.dp, top = 10.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                CompensationComp()
                Divider(
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp, bottom = 10.dp, top = 10.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                DurationComp()
                Divider(
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp, bottom = 10.dp, top = 10.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LoginButton(onClick = { /* TODO */ }, R.string.filtrerKnap, true)
                }
            }
        },
        bottomBar = {

        }
    )
}

@Composable
fun DistanceComp() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Text(
            text = stringResource(R.string.afstand),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.postnr),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            //Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(text = stringResource(R.string.postnrPlaceholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.scale(0.6f)
            )
        }
        Text(
            text = stringResource(R.string.maksAfstand),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun CompensationComp() {
    var reward by remember { mutableStateOf(false) }
    var transport by remember { mutableStateOf(false) }
    var work by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(
            text = stringResource(R.string.kompensation),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Row {
            CheckOption(
                text = stringResource(R.string.honorar),
                checked = reward,
                onClick = { reward = !reward }
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckOption(
                text = stringResource(R.string.koersel),
                checked = transport,
                onClick = { transport = !transport }
            )
        }
        CheckOption(
            text = stringResource(R.string.arbejdsgodtgoersel),
            checked = work,
            onClick = { work = !work }
        )
    }
}

@Composable
fun DurationComp() {
    var time1 by remember { mutableStateOf(false) }
    var time2 by remember { mutableStateOf(false) }
    var time3 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 10.dp)) {
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
                    }
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
                    }
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
                    }
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






@Preview
@Composable
fun FilterPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
        FilterScreen()
    }
}
