package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
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
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.UserInfo
import com.example.pb1_probe_application.dataClasses.UserPatient
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EditProfileScreen(role: Role,onClick: () -> Unit) {

    if (role == Role.TRIAL_PARTICIPANT)
        EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current, onClick = onClick)
    if (role == Role.RESEARCHER)
        EditUserInfoList(userInfoList = Datasource().loadProfileResearcherInfo(), focusManager = LocalFocusManager.current, onClick = onClick)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditUserInfoList(userInfoList: List<UserInfo>, focusManager: FocusManager, modifier: Modifier = Modifier,onClick: () -> Unit) {
    var input by remember { mutableStateOf("") }

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
                        val data = UserPatient()
                        val s: String = FirebaseAuth.getInstance().currentUser!!.uid
                        //TODO connect to database
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
                    EditUserInfoField(
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
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp)
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

//@Preview
//@Composable
//private fun ProfileUserScreenPreview() {
//    EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
//}