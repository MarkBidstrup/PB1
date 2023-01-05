package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?, userViewModel: UserViewModel, navigateBack: () -> Unit){

    val signupFlow = authViewModel?.signupFlow?.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeated by remember { mutableStateOf("") }

    var participantChecked by remember { mutableStateOf(false) }
    var researcherChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick =navigateBack)
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(text = stringResource(R.string.createProfile), style = Typography.h2)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.trialParticipantOrResearcher), style = Typography.body1)
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = participantChecked,
                    onCheckedChange = {
                        participantChecked = it
                        if(participantChecked)
                            researcherChecked = false
                    }
                )
                Text(text = stringResource(R.string.trialParticipant))
                Spacer(Modifier.width(10.dp))
                Checkbox(
                    checked = researcherChecked,
                    onCheckedChange = {
                        researcherChecked = it
                        if(researcherChecked)
                            participantChecked = false
                    }
                )
                Text(text = stringResource(R.string.researcher))
                Spacer(Modifier.width(15.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))
            textField(label = stringResource(R.string.email), text = email, onValueChange = {email = it}, focusManager = LocalFocusManager.current)
            Spacer(modifier = Modifier.height(15.dp))
            textField(label = stringResource(R.string.password), text = password, hiddenText = true, onValueChange = {password = it}, focusManager = LocalFocusManager.current)
            Spacer(modifier = Modifier.height(15.dp))
            textField(label = stringResource(R.string.repeatPassword), text = passwordRepeated, hiddenText = true, onValueChange = {passwordRepeated = it}, focusManager = LocalFocusManager.current)

            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(onClick = { signup(authViewModel, userViewModel, email, password, passwordRepeated, context, participantChecked, researcherChecked)},
                text = R.string.registrer, filled = true)
            Spacer(modifier = Modifier.height(20.dp))

            // changes for register
            signupFlow?.value?.let {
                when (it) {
                    is Resource.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        Toast.makeText(context,R.string.registrationComplete,Toast.LENGTH_LONG).show()
                        LaunchedEffect(Unit) {
                            val role = if (participantChecked) Role.TRIAL_PARTICIPANT else Role.RESEARCHER
                            val uid = authViewModel!!.currentUser!!.uid
                            userViewModel.createUser(uid,email, role)
                            userViewModel.setCurrentUser(uid)
                            navHostController?.navigate("FurtherInformation")
                        }
                    }
                }
            }
        }
        }
    )
}

private fun signup(authViewModel: AuthViewModel?, userViewModel: UserViewModel, email: String, password: String, passwordRepeated: String, context: Context, participantChecked: Boolean, researcherChecked: Boolean) {
    if(!participantChecked && !researcherChecked)
        Toast.makeText(context,R.string.trialParticipantOrResearcher,Toast.LENGTH_LONG).show()
    else if(email == "")
        Toast.makeText(context,R.string.noEmail,Toast.LENGTH_LONG).show()
    else if(password == "")
        Toast.makeText(context,R.string.noPassword,Toast.LENGTH_LONG).show()
    else if (password != passwordRepeated)
        Toast.makeText(context,R.string.passwordNotRepeated,Toast.LENGTH_LONG).show()
    else {
        authViewModel?.signup(email,password)
    }
}

