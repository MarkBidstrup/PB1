package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.DropDownType
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.UserInfo
import com.example.pb1_probe_application.dataClasses.UserPatient
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FurtherInformationScreen(role: Role, onClick: () -> Unit) {

    if (role == Role.TRIAL_PARTICIPANT)
        FurtherInformationList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current, onClick = onClick)
    if (role == Role.RESEARCHER)
        FurtherInformationList(userInfoList = Datasource().loadProfileResearcherInfo(), focusManager = LocalFocusManager.current, onClick = onClick)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FurtherInformationList(userInfoList: List<UserInfo>, focusManager: FocusManager, modifier: Modifier = Modifier, onClick: () -> Unit) {
    var input by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.IndtastYderligereOplysninger), style = Typography.h1, ) },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) { }
        },
        content = {
            LazyColumn {
                items(userInfoList) { UserInfo ->
                    FurtherInformationField(
                        userInfo = UserInfo,
                        label = R.string.placeholder, // TODO: make this variable
                        inputField = input,
                        onChange = { input = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                    if (userInfoList.lastIndexOf(element = UserInfo) != userInfoList.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        Column (
                            modifier = modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        LoginButton(
                            onClick = { OpretProfilonClick() },
                            text = R.string.createProfileCAPS,
                            filled = true)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        },
    )

}
@Composable
fun FurtherInformationField(
    userInfo: UserInfo,
    @StringRes label: Int,
    inputField: String,
    onChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier
) {

    Column {
        Text(
            text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        if (LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.koen)) {
            DropDownState(DropDownType.KOEN,onChange, inputField)
        } else if (
            LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.alder)
            || LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.vaegt)
            || LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.telefon)
        ) {
            OutlinedTextField(
                value = inputField,
                singleLine = true,
                label = { Text(text = stringResource(label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                onValueChange = onChange,
                textStyle = Typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions
            )
        } else {
            OutlinedTextField(
                value = inputField,
                singleLine = true,
                label = { Text(text = stringResource(label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                onValueChange = onChange,
                textStyle = Typography.body1,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
        }
    }
}

@Composable
fun DropDownState(dropDownType: DropDownType, onValueChange: (String) -> Unit, startValue: String) {
    val listItems = Datasource().loadDropDownList(dropDownType)

    if (dropDownType == DropDownType.KOEN || dropDownType == DropDownType.JA_NEJ) {
        listItems?.let { DropDown(it, onValueChange = onValueChange, startValue) }
    }
    if (dropDownType == DropDownType.KOMMUNE)
        listItems?.let { DropDownFilter(it, onValueChange = onValueChange, startValue) }
}


// This drop down function has taken inspiration from: https://semicolonspace.com/dropdown-menu-jetpack-compose/
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDown(listItems: List<String>, onValueChange: (String) -> Unit, startValue: String) {

    var selectedItem by remember {
        mutableStateOf(startValue)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(start = 17.dp, end = 17.dp)
            .border(0.5.dp, Color.DarkGray, RoundedCornerShape(4.dp))

    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedItem,
                onValueChange = {
                    onValueChange(it)
                    selectedItem = it
                                },
                readOnly = true,
                label = {  },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.Unspecified)
            )

            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listItems?.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(onClick = {
                        selectedItem = selectedOption
                        onValueChange(selectedItem)
                        expanded = false
                    }) {
                        Text(text = selectedOption)
                    }
                }
            }
        }
    }
}

// This drop down function has taken inspiration from: https://semicolonspace.com/dropdown-menu-jetpack-compose/
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownFilter(listItems: List<String> , onValueChange: (String) -> Unit, startValue: String) {
    var selectedItem by remember {
        mutableStateOf(startValue)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(start = 17.dp, end = 17.dp)
            .border(0.5.dp, Color.DarkGray, RoundedCornerShape(4.dp))

    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedItem,
                onValueChange = {
                    onValueChange(it)
                    selectedItem = it
                },
                label = {  },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.Unspecified)
            )

            val filteringOptions =
                listItems?.filter { it.contains(selectedItem, ignoreCase = true) }

            if (filteringOptions != null) {
                if (filteringOptions.isNotEmpty()) {

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filteringOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = selectionOption
                                    onValueChange(selectedItem)
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun BoolToString(boolean: Boolean): String {
    if (boolean)
        return "Ja"
    else
        return "Nej"
}

fun StringToBool(string: String): Boolean {
    return string == "Ja"
}

fun OpretProfilonClick(){
    val data = UserPatient()
    val s: String = FirebaseAuth.getInstance().currentUser!!.uid
    //TODO connect to database
}

