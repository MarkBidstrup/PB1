package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun EditProfileScreen(role: Role,onClick: () -> Unit, logOutNav : () -> Unit, trialsViewModel: TrialsViewModel, authViewModel: AuthViewModel?, userViewModel: UserViewModel) {

    if (role == Role.TRIAL_PARTICIPANT)
        EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current, onClick = onClick, logOutNav = logOutNav, trialsViewModel = trialsViewModel, authViewModel = authViewModel, userViewModel = userViewModel)
    if (role == Role.RESEARCHER)
        EditUserInfoList(userInfoList = Datasource().loadProfileResearcherInfo(), focusManager = LocalFocusManager.current, onClick = onClick, logOutNav = logOutNav, trialsViewModel = trialsViewModel, authViewModel = authViewModel, userViewModel = userViewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditUserInfoList(userInfoList: List<UserInfo>, focusManager: FocusManager, modifier: Modifier = Modifier,onClick: () -> Unit, logOutNav :() -> Unit, trialsViewModel: TrialsViewModel, authViewModel: AuthViewModel?, userViewModel: UserViewModel) {
    val uid = authViewModel!!.currentUser!!.uid
    userViewModel.setCurrentUser(uid)
    val data = remember { userViewModel.currentUserData }
    var edited by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.editProfileHeading), style = Typography.h1, ) },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        if (edited) {
                            userViewModel.saveUserData(uid,data)
                            edited = false
                        }
                        /*f (data is UserPatient) {
                            data.
                        }
                        if (data is UserResearcher) {
                            data.
                        }*/


                    }
                ) {
                    Icon(
                        Icons.Default.Save,
                        contentDescription = "save changes",
                    )
                }
                IconButton(
                     onClick = {
                         onClick()
                     }
                ) {
                     Icon(
                         Icons.Default.ArrowBack,
                         contentDescription = "return",
                     )
                }
            }
                },
        content = {
            LazyColumn {
                itemsIndexed(userInfoList) { i, UserInfo ->
                    var input by remember { mutableStateOf("") }
                    if (data is UserPatient) {
                        when (UserInfo.profileInfo) {
                            userInfoAttributes.firstName -> input = data.name
                            userInfoAttributes.lastName -> input = data.lastName
                            userInfoAttributes.email -> input = data.email
                            userInfoAttributes.tlf -> input = data.phone
                            userInfoAttributes.age -> input = data.age
                            userInfoAttributes.gender -> input = data.gender
                            userInfoAttributes.weight -> input = data.weight
                            userInfoAttributes.diagnosis -> input = data.diagnosis
                            else -> {input = ""}
                        }
                    }
                    if (data is UserResearcher) {
                        when (UserInfo.profileInfo) {
                            userInfoAttributes.firstName -> input = data.name
                            userInfoAttributes.lastName -> input = data.lastName
                            userInfoAttributes.email -> input = data.email
                            userInfoAttributes.tlf -> input = data.phone
                            userInfoAttributes.institute -> input = data.department
                            else -> {input = ""}
                        }
                    }
                    var userInput by remember { mutableStateOf(input) }
                    EditUserInfoField(
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
                        edited = true
                        if (data is UserPatient) {
                            when (UserInfo.profileInfo) {
                                userInfoAttributes.firstName -> data.name = userInput
                                userInfoAttributes.lastName -> data.lastName = userInput
                                userInfoAttributes.gender -> data.gender = userInput
                                userInfoAttributes.age -> data.age = userInput
                                userInfoAttributes.weight -> data.weight = userInput
                                userInfoAttributes.diagnosis -> data.diagnosis = userInput
                                userInfoAttributes.email -> data.email = userInput
                                userInfoAttributes.tlf -> data.phone = userInput
                                else -> {}
                            }
                        }
                        if (data is UserResearcher) {
                            when (UserInfo.profileInfo) {
                                userInfoAttributes.firstName -> data.name = userInput
                                userInfoAttributes.lastName -> data.lastName = userInput
                                userInfoAttributes.institute -> data.department = userInput
                                userInfoAttributes.email -> data.email = userInput
                                userInfoAttributes.tlf -> data.phone = userInput
                                else -> {}
                            }
                        }
                    }
                    if (userInfoList.lastIndexOf(element = UserInfo) != userInfoList.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp),
                            thickness = 1.dp,
                            color = androidx.compose.ui.graphics.Color.LightGray
                        )
                    } else {
                        Divider(
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp),
                            thickness = 1.dp,
                            color = androidx.compose.ui.graphics.Color.LightGray
                        )
                        Text(
                            text = stringResource(R.string.sletProfil),
                            style = Typography.body1,
                            color = TextColorRed,
                            modifier = Modifier
                                .padding(start = 17.dp, end = 17.dp)
                                .clickable {
                                    authViewModel.delete()
                                    trialsViewModel.deleteCurrentUserFromAllTrialDBEntries()
                                    userViewModel.deleteUser(uid)
                                    logOutNav()
                                }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
    )
}
@Composable
fun EditUserInfoField(
    userInfo: UserInfo,
    @StringRes label: Int,
    inputField: String,
    onChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier) {

    Column {
        Text(
            text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        if (LocalContext.current.getString(userInfo.StringResourceHeaderId) == stringResource(id = R.string.koen)) {
            DropDownState(dropDownType = DropDownType.KOEN, onChange, inputField,null)
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

//@Composable
//fun DeleteUserDialog() {
//    AlertDialog(
//        onDismissRequest = {
//                           /*TODO*/
//                           },
//        title = { Text(stringResource(id = R.string.sletProfil))},
//        text = { Text("Er du sikker på du vil slette din profil?") },
//        confirmButton = {
//                        Button(onClick = { /*TODO*/ }) {
//                            Text(text = "SLET PROFIL")
//                        }
//        },
//        dismissButton = {
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "FORTRYD")
//            }
//        }
//    )
//}