package com.example.pb1_probe_application


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.navigation.BottomNavigation
import com.example.pb1_probe_application.navigation.Route
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController : NavHostController
    lateinit var authViewModel: AuthViewModel
    lateinit var trialsViewModel: TrialsViewModel
    lateinit var userViewModel: UserViewModel

    @Before
    fun navHost() {
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


