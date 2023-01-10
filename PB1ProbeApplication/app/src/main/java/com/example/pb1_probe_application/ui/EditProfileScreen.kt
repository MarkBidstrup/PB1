package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.pb1_probe_application.data.auth.Resource

@Composable
fun EditProfileScreen(role: Role,onClick: () -> Unit, deleteNav : () -> Unit, authViewModel: AuthViewModel?, userViewModel: UserViewModel) {

    if (role == Role.TRIAL_PARTICIPANT)
        EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current, onClick = onClick, deleteNav = deleteNav, authViewModel = authViewModel, userViewModel = userViewModel)
    if (role == Role.RESEARCHER)
        EditUserInfoList(userInfoList = Datasource().loadProfileResearcherInfo(), focusManager = LocalFocusManager.current, onClick = onClick, deleteNav = deleteNav, authViewModel = authViewModel, userViewModel = userViewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun EditUserInfoList(userInfoList: List<UserInfo>, focusManager: FocusManager, onClick: () -> Unit, deleteNav :() -> Unit, authViewModel: AuthViewModel?, userViewModel: UserViewModel) {
    val uid = authViewModel!!.currentUser!!.uid
    userViewModel.setCurrentUser(uid)
    val data = remember { userViewModel.currentUserData }
    var updatedEmail by remember { mutableStateOf("") }
    var edited by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val updateEmailFlow = authViewModel.updateEmailFlow.collectAsState()
    var emailEdited = false
    var toastErrorShow by remember { mutableStateOf(true) }

    //userViewModel.getViewModelUserData("BJUd41JgLShgB5NvBTr1nNHkipk1")
    //var user = userViewModel.userDataFlow.collectAsState().value as UserResearcher


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.editProfileHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        toastErrorShow = true // displays a toast message
                        if (edited && !emailEdited) {
                            userViewModel.saveUserData(uid,data)
                            Toast.makeText(context, R.string.changesSaved, Toast.LENGTH_LONG).show()
                            edited = false
                            onClick()
                        } else if (emailEdited) {
                            authViewModel.updateEmail(updatedEmail)
                            updateEmailFlow.value.let {
                                when (it) {
                                    is Resource.Failure -> {
                                        if (toastErrorShow) {
                                            Toast.makeText(context, it.exception.message,Toast.LENGTH_SHORT).show()
                                            toastErrorShow = false
                                        }
                                    }
                                    is Resource.Success -> {
                                        if(edited) // email and other info updated
                                            Toast.makeText(context, R.string.changesSaved,Toast.LENGTH_LONG).show()
                                        else // only email updated
                                            Toast.makeText(context, R.string.ændretEmail,Toast.LENGTH_LONG).show()
                                        data.email = updatedEmail
                                        userViewModel.saveUserData(uid,data)
                                        onClick()
                                    }
                                    else -> {}
                                }
                            }
                        } else {
                            Toast.makeText(context, R.string.noChangesMade, Toast.LENGTH_LONG).show()
                        }

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
                items(userInfoList) { UserInfo ->
                    var input by remember { mutableStateOf("") }
                    if (data is UserPatient) {
                        input = when (UserInfo.userInfoType) {
                            UserInfoTypes.FirstName -> data.name
                            UserInfoTypes.LastName -> data.lastName
                            UserInfoTypes.Email -> data.email
                            UserInfoTypes.Phone -> data.phone
                            UserInfoTypes.Age -> data.age
                            UserInfoTypes.Gender -> data.gender
                            UserInfoTypes.Weight -> data.weight
                            UserInfoTypes.Diagnosis -> listToString(data.diagnosis)
                            else -> { "" }
                        }
                    }
                    if (data is UserResearcher) {
                        input = when (UserInfo.userInfoType) {
                            UserInfoTypes.FirstName -> data.name
                            UserInfoTypes.LastName -> data.lastName
                            UserInfoTypes.Email -> data.email
                            UserInfoTypes.Phone -> data.phone
                            UserInfoTypes.Department -> data.department
                            else -> { "" }
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
                    if (userInput != input && UserInfo.userInfoType != UserInfoTypes.Email) {
                        edited = true
                        if (data is UserPatient) {
                            when (UserInfo.userInfoType) {
                                UserInfoTypes.FirstName -> data.name = userInput
                                UserInfoTypes.LastName -> data.lastName = userInput
                                UserInfoTypes.Gender -> data.gender = userInput
                                UserInfoTypes.Age -> data.age = userInput
                                UserInfoTypes.Weight -> data.weight = userInput
                                UserInfoTypes.Diagnosis -> data.diagnosis = makeList(userInput)
                                UserInfoTypes.Phone -> data.phone = userInput
                                else -> {}
                            }
                        }
                        if (data is UserResearcher) {
                            when (UserInfo.userInfoType) {
                                UserInfoTypes.FirstName -> data.name = userInput
                                UserInfoTypes.LastName -> data.lastName = userInput
                                UserInfoTypes.Department -> data.department = userInput
                                UserInfoTypes.Phone -> data.phone = userInput
                                else -> {}
                            }
                        }
                    }
                    if (userInput != input && UserInfo.userInfoType == UserInfoTypes.Email) {
                        emailEdited = true
                        updatedEmail = userInput
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
                            text = stringResource(R.string.nulstilKodeord),
                            style = Typography.body1,
                            modifier = Modifier
                                .padding(start = 17.dp, end = 17.dp)
                                .clickable {
                                    authViewModel.resetPassword(data.email)
                                    Toast
                                        .makeText(context, R.string.tjekEmail, Toast.LENGTH_LONG)
                                        .show()
                                }
                        )
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
                                    deleteNav()
                                }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
    )
}


fun listToString(list: List<String>): String {
    var result = ""
    for (string in list) {
        result += string
        if (string != list.last()) { result += ", " }
    }
    return result
    //.toString().drop(1).dropLast(1)
}

@Composable
fun EditUserInfoField(
    userInfo: UserInfo,
    @StringRes label: Int,
    inputField: String,
    onChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions) {

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