package com.example.pb1_probe_application.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.data.UserInfoState
import com.example.pb1_probe_application.model.UserInfo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun EditProfileScreen(user: String) {

    val currentUser: String = user
    val focusManager = LocalFocusManager.current

    if (currentUser.equals("patient"))
        EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
    if (currentUser.equals("researcher"))
        EditUserInfoList(userInfoList = Datasource().loadProfileResercherInfo(), focusManager = LocalFocusManager.current)
}

@Composable
fun EditUserInfoList(userInfoList: List<UserInfo>, focusManager: FocusManager, modifier: Modifier = Modifier) {


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.editProfileHeading), style = Typography.h1) },
                backgroundColor = androidx.compose.ui.graphics.Color.White)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                 IconButton(onClick = { /*TODO*/ }) {
                     Icon(
                         Icons.Default.ArrowBack,
                         contentDescription = "edit",
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
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                )
                            )
                            if (!(userInfoList.lastIndexOf(element = UserInfo) == userInfoList.lastIndex)) {
                                Divider(
                                    modifier = Modifier.padding(10.dp),
                                    thickness = 1.dp,
                                    color = androidx.compose.ui.graphics.Color.LightGray
                                )
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
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier) {

    var input by remember { mutableStateOf("") }

    Column {
        Text(
            text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        OutlinedTextField(
            value = input,
            singleLine = true,
            label = { Text(text = stringResource(label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
    EditUserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), focusManager = LocalFocusManager.current)
}