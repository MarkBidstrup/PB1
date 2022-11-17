package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.CreateTrialField
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun CreateTrialScreen() {

    val focusManager = LocalFocusManager.current

    CreateTrialList(createTrialList = Datasource().loadCreateTrialList(), focusManager = focusManager)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateTrialList(createTrialList: List<CreateTrialField>, focusManager: FocusManager, modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {

            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.opretNytStudie), style = Typography.h1, ) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = {
                        //TODO: implement onClick

                    }) {
                    Icon(

                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.padding(bottom = 0.dp)) {
                LazyColumn (
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)) {
                    items(createTrialList) { CreateTrialField ->
                        CreateTrialField(
                            createTrialField = CreateTrialField,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            )
                        )
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
                    LoginButton(onClick = {
                        //TODO: implement onClick
                    }, R.string.annuller, false)
                    LoginButton(onClick = {
                        //TODO: implement onClick
                    }, R.string.bekræft, true)
                }
            }
        },
    )

}



@Composable
fun CreateTrialField(
    createTrialField: CreateTrialField,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier) {

    var input by remember { mutableStateOf("") }

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
            label = { Text(text = LocalContext.current.getString(createTrialField.StringResourceFieldText)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            onValueChange = { input = it },
            textStyle = Typography.body1,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Preview
@Composable
private fun ProfileUserScreenPreview() {
//    EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
}