package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?, navigateBack: () -> Unit){

    val signupFlow = authViewModel?.signupFlow?.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeated by remember { mutableStateOf("") }

    var participantChecked by remember { mutableStateOf(false) }
    var researcherChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // TODO - implement sign up logic, including checking if two entered passwords are the same etc.
    // add user error message if passwords are not the same or if no role box has been checked?
    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick =navigateBack)
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
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
                Checkbox(
                    checked = researcherChecked,
                    onCheckedChange = {
                        researcherChecked = it
                        if(researcherChecked)
                            participantChecked = false
                    }
                )
                Text(text = stringResource(R.string.researcher))
            }

            Spacer(modifier = Modifier.height(50.dp))
            textField(label = stringResource(R.string.email), text = email, onValueChange = {email = it})
            Spacer(modifier = Modifier.height(15.dp))
            textField(label = stringResource(R.string.password), text = password, onValueChange = {password = it})
            Spacer(modifier = Modifier.height(15.dp))
            textField(label = stringResource(R.string.repeatPassword), text = passwordRepeated, onValueChange = {passwordRepeated = it})

            Spacer(modifier = Modifier.height(40.dp))
            LoginButton(onClick = {
                if(participantChecked || researcherChecked) {
                    val role =
                        if(participantChecked)
                            Role.TRIAL_PARTICIPANT
                        else
                            Role.RESEARCHER
                    signup(authViewModel, email, password, context, role)
                }
                  },
                text = R.string.registrer, filled = true)
        }
            // changes for register
            signupFlow?.value?.let {
                when(it) {
                    is Resource.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Loading -> {
                    }
                    is Resource.Success -> {
                    }
                }
            }
        }
    )
}

private fun signup(authViewModel: AuthViewModel?, email: String, password: String, context: Context, role: Role) {
    authViewModel?.signup(email,password)
    // TODO - make sure the role is saved with the profile
    if (!(email.equals("") && password.equals("")))
        Toast.makeText(context,"Registration completed",Toast.LENGTH_LONG).show()
}

