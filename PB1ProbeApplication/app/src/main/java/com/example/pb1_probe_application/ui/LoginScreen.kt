package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LogInScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?, userViewModel: UserViewModel, navigateBack: () -> Unit){

    // changes for login
    val loginFlow = authViewModel?.loginFlow?.collectAsState()

    var email by remember { mutableStateOf("deltager@mail.com") }
    var password by remember { mutableStateOf("123456") }
    var toastErrorShow by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick = navigateBack)
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = stringResource(R.string.logInd2), style = Typography.h2)
            Spacer(modifier = Modifier.height(20.dp))
            textField(
                label = stringResource(R.string.email),
                text = email,
                onValueChange = { email = it },
                focusManager = LocalFocusManager.current
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                textField(
                    label = stringResource(R.string.password),
                    text = password,
                    hiddenText = true,
                    onValueChange = { password = it },
                    focusManager = LocalFocusManager.current
                )
                Text(text = stringResource(R.string.glemtKodeord), fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable {
                            if (authViewModel != null) {
                                authViewModel.forgottenEmail = email
                            }
                            navHostController?.navigate("ForgottenPassword")
                        })
            }

            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(
                onClick = {
                    toastErrorShow = true
                    authViewModel?.login(email, password) },
                text = R.string.logInd,
                filled = true
            )
            Spacer(modifier = Modifier.height(80.dp))

            // code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding
            // with own additions
            loginFlow?.value?.let {
                when (it) {
                    is Resource.Failure -> {
//                    val context = LocalContext.current
                        if (toastErrorShow) {
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                            toastErrorShow = false
                        }
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            userViewModel.setCurrentUser(authViewModel.currentUser!!.uid)
                            navHostController?.navigate("Home") {
                                popUpTo("Home") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }
    )
}

@Composable
fun textField(label: String, text: String, hiddenText: Boolean = false, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .border(1.dp, ButtonColorGreen, RoundedCornerShape(4.dp))

    ) {
        if(hiddenText)
            TextField(
                label = { Text(text = label) },
                value = text,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = ButtonColorGreen,
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier.padding(5.dp),
                onValueChange = onValueChange)
        else
            TextField(
                label = { Text(text = label) },
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = ButtonColorGreen,
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier.padding(5.dp),
                onValueChange = onValueChange)
    }
}
