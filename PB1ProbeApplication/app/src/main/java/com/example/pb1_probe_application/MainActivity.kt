package com.example.pb1_probe_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.navigation.MainHome
import com.example.pb1_probe_application.ui.*


import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()
    private val trialsViewModel by viewModels<TrialsViewModel>()
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PB1ProbeApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    ManageTrialScreen()
                      MainHome(authViewModel, trialsViewModel, userViewModel)
//                    NotificationsScreen()
//                    ManageTrialScreen()
//                    ManageTrialScreen(trialsViewModel = trialsViewModel, navBack = { /*TODO*/ }) {
//                        
//                    }
//                    FurtherInformationScreen(role = Role.TRIAL_PARTICIPANT) {
//
//                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PB1ProbeApplicationTheme {
    }
}

