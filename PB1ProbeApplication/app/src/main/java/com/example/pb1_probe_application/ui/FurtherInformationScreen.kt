package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FurtherInformationScreen(role: Role, authViewModel: AuthViewModel?, userViewModel: UserViewModel, onClick: () -> Unit) {
    val focusManager = LocalFocusManager.current
    val uid = authViewModel!!.currentUser!!.uid
    val userInfoList = when(role) {
        Role.TRIAL_PARTICIPANT -> Datasource().loadProfilePatientInfo()
        Role.RESEARCHER -> Datasource().loadProfileResearcherInfo()
    }
    userViewModel.setCurrentUser(uid)
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.IndtastYderligereOplysninger), style = Typography.h1 ) },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
        },
        content = {
            val data: UserData = when(role) {
                Role.TRIAL_PARTICIPANT -> UserPatient()
                Role.RESEARCHER -> UserResearcher()
            }

            Column (modifier = Modifier
                .verticalScroll(scrollState)){
                userInfoList.forEach { UserInfo ->
                    val input by remember { mutableStateOf("") }
                    var userInput by remember { mutableStateOf(input) }
                    FurtherInformationField(
                        userInfo = UserInfo,
                        label = UserInfo.StringResourceHeaderId,
                        inputField = userInput,
                        onChange = { userInput = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                    if (userInput != input) {
                        if (data is UserPatient) {
                            when (UserInfo.userInfoType) {
                                UserInfoTypes.FirstName -> data.name = userInput
                                UserInfoTypes.LastName -> data.lastName = userInput
                                UserInfoTypes.Gender -> data.gender = userInput
                                UserInfoTypes.Age -> data.age = userInput
                                UserInfoTypes.Weight -> data.weight = userInput
                                UserInfoTypes.Diagnosis -> data.diagnosis = makeList(userInput)
                                UserInfoTypes.Email -> data.email = userInput
                                UserInfoTypes.Phone -> data.phone = userInput
                                else -> {}
                            }
                        }
                        if (data is UserResearcher) {
                            when (UserInfo.userInfoType) {
                                UserInfoTypes.FirstName -> data.name = userInput
                                UserInfoTypes.LastName -> data.lastName = userInput
                                UserInfoTypes.Department -> data.department = userInput
                                UserInfoTypes.Email -> data.email = userInput
                                UserInfoTypes.Phone -> data.phone = userInput
                                else -> {}
                            }
                        }
                    }
                    if (userInfoList.lastIndexOf(element = UserInfo) != userInfoList.lastIndex
                        && UserInfo.StringResourceHeaderId != R.string.email
                    ) {
                        Divider(
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    } else if (UserInfo.StringResourceHeaderId != R.string.email){
                        Spacer(modifier = Modifier.height(20.dp))
                        Column (
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        LoginButton(
                            onClick = {
                                if(data.name == "" || data.lastName == "")
                                    Toast.makeText(context,R.string.noName, Toast.LENGTH_LONG).show()
                                else {
                                    data.email = userViewModel.currentUserData.email
                                    userViewModel.saveUserData(uid, data)
                                    Toast.makeText(context,R.string.infoGemt, Toast.LENGTH_LONG).show()
                                    onClick()
                                }
                              },
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
@SuppressLint("SuspiciousIndentation")
@Composable
fun FurtherInformationField(
    userInfo: UserInfo,
    @StringRes label: Int,
    inputField: String,
    onChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {

    Column {
        if (LocalContext.current.getString(userInfo.StringResourceHeaderId) != stringResource(id = R.string.email))
            Text(
                text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
                modifier = Modifier.padding(start = 17.dp),
                style = MaterialTheme.typography.body1,
                color = TextColorGreen
            )
        if (LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.koen)) {
            DropDownState(DropDownType.KOEN,onChange, inputField, null)
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
        } else if (LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.email)) {
            // don't show email here
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
fun DropDownState(dropDownType: DropDownType, onValueChange: (String) -> Unit, startValue: String, keyboardActions: KeyboardActions?) {
    val listItems = Datasource().loadDropDownList(dropDownType)

    if (dropDownType == DropDownType.KOEN || dropDownType == DropDownType.JA_NEJ) {
        listItems?.let { DropDown(it, onValueChange = onValueChange, startValue) }
    }
    if (dropDownType == DropDownType.KOMMUNE || dropDownType == DropDownType.DIAGNOSER )
        listItems?.let {
            if (keyboardActions != null) {
                DropDownFilter(it, onValueChange = onValueChange, startValue, keyboardActions)
            }
        }
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
                listItems.forEach { selectedOption ->
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
fun DropDownFilter(listItems: List<String> , onValueChange: (String) -> Unit, startValue: String, keyboardActions: KeyboardActions) {
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = keyboardActions,
                label = {  },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.Unspecified)
            )

            val filteringOptions =
                listItems.filter { it.contains(selectedItem, ignoreCase = true) }

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

fun boolToString(boolean: Boolean): String {
    return if (boolean)
        "Ja"
    else
        "Nej"
}

fun stringToBool(string: String): Boolean {
    return string == "Ja"
}

