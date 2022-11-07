package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun DeltagerInfo(trialID: String, navHostController: NavHostController = rememberNavController()) {
    // TODO - get data from database based on the trialID
    val data = Datasource().loadDeltagerInfo()

    var consentBoxChecked by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState() // alternatively used to enable "apply" button

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.deltager_info), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                //TODO - go back
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier
                .padding(17.dp)
                .padding(bottom = 20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState) ){
                Text(text = data)
                Row (modifier = Modifier.padding(end = 20.dp)){
                    Checkbox(
                        checked = consentBoxChecked,
                        onCheckedChange = { consentBoxChecked = it }
                    )
                    Text(text = stringResource(R.string.samtykkeerklaering),
                        lineHeight = 20.sp)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        },
        bottomBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                TrialApplyButton(
                    consentBoxChecked, // scrollState.value == scrollState.maxValue
                    onClick = {
                        // TODO - update state
                        navHostController.navigate("Applied")
                })
            }
        }
        )
}