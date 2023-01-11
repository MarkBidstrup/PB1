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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.navigation.MainHome
import com.example.pb1_probe_application.ui.*


import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
//                    DeltagerListeScreen(id = String(),trialsViewModel = trialsViewModel,navHostController = rememberNavController(
//                    ),userViewModel)

//                    ReadMoreTrialPostScreen( trialsViewModel.currentNavTrial!!, onClickNav = null)
                      MainHome(authViewModel, trialsViewModel, userViewModel)
                      onDestroy()
//                    NotificationsScreen()
//                    ManageTrialScreen()
//                    ManageTrialScreen(trialsViewModel = trialsViewModel, navBack = { /*TODO*/ }, onClickNavToEditTrial = {
//
//                    }, onClickNav = {
//
//
//                    }, navDelete = {
//                    })

//                    FurtherInformationScreen(role = Role.TRIAL_PARTICIPANT) {
//
//                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authViewModel.logout()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PB1ProbeApplicationTheme {
    }
}

