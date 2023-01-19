package com.example.pb1_probe_application


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.auth.AuthRepository
import com.example.pb1_probe_application.data.auth.AuthRepositoryImpl
import com.example.pb1_probe_application.data.trials.TrialRepositoryImpl
import com.example.pb1_probe_application.data.userData.UserDataRepoImpl
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.navigation.BottomNavigation
import com.example.pb1_probe_application.navigation.Route
import com.example.pb1_probe_application.navigation.notificationNav
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Auth
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController
    lateinit var authViewModel: AuthViewModel
    lateinit var trialsViewModel: TrialsViewModel
    lateinit var userViewModel: UserViewModel


    @Before
    fun navHost(
    ) {
        authViewModel = AuthViewModel(AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance()))
        trialsViewModel = TrialsViewModel(TrialRepositoryImpl(firestore = FirebaseFirestore.getInstance(), auth = AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())),UserDataRepoImpl(firestore = FirebaseFirestore.getInstance()))
        userViewModel =  UserViewModel(UserDataRepoImpl(firestore = FirebaseFirestore.getInstance()))

        composeTestRule.setContent {
            navController = NavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BottomNavigation(navController = navController, authViewModel = authViewModel, trialsViewModel = trialsViewModel, userViewModel = userViewModel)
        }
    }
    @Test
    fun navigateToHomeScreen() {
        composeTestRule.onNodeWithText("Home")
            .assertIsDisplayed()
    }
    @Test
    fun navigateToFilterScreen() {
        navController.navigate(Route.Filter.route)
        composeTestRule.onNodeWithText("Filter")
            .assertIsDisplayed()
    }
    @Test
    fun navTest(){
        composeTestRule.onNodeWithText(R.string.logInd.toString())
            .performClick()
        val toLoginScreenRoute = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(toLoginScreenRoute,"logInd")
    }
}


