package com.example.pb1_probe_application.ui

import android.widget.Toast
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.navigation.BottomBarItems

import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.navigation.Graph
import com.example.pb1_probe_application.navigation.Route
import com.example.pb1_probe_application.ui.theme.Cairo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen


@Composable
fun MainHome(authViewModel: AuthViewModel, trialsViewModel: TrialsViewModel){
    val navController = rememberNavController()
    authViewModel.logout() // TODO - temp solution - figure out how to log out after activity ends
    BottomNavGraph(navController = navController, authViewModel = authViewModel, trialsViewModel = trialsViewModel)
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarItems.Home,
        BottomBarItems.Trials,
        BottomBarItems.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        // Customize navigationBAR here :)
        backgroundColor = NavBarColorGreen,
    ) {
        screens.forEach{
                screens ->
            addItem(screen = screens, currentDestination = currentDestination , navController = navController)
        }
    }
}

@Composable
fun RowScope.addItem(
    screen: BottomBarItems,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(
        label = {
            Text(text = screen.title,
                fontFamily = Cairo,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        // unselected items will appear less brighter with this code
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                // this code will make sure that we can head back to
                // our start screen destination when we click on back button (Home screen)
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun BottomNavGraph(navController: NavHostController, authViewModel: AuthViewModel?, trialsViewModel: TrialsViewModel) {
    val ctx = LocalContext.current
    NavHost(navController = navController,
        startDestination = BottomBarItems.Home.route
    )
    {


        composable(route = BottomBarItems.Home.route) {
            var loggedIn by remember { mutableStateOf(false)  }
            loggedIn = authViewModel?.currentUser != null
            TrialListingsScreen(trialsViewModel = trialsViewModel, navHostController = navController, loggedIn = loggedIn)
        }
        composable(route = Route.Filter.route) {
            FilterScreen(onClickNav = {
                navController.navigate("Home")
            })
        }

        composable(route = BottomBarItems.Trials.route) {
            MyTrials(trialsViewModel = trialsViewModel, navHostController = navController)
        }

        composable(route = BottomBarItems.Profile.route) {

            ProfileScreen(role = Role.TRIAL_PARTICIPANT, navHostController = navController)
        }

        navigationAppHost(navController = navController, authViewModel)
        notificationNav(navController = navController)
        deltagerInfoNav(navController = navController,trialsViewModel = trialsViewModel)

        // navigate to editprofile screen
        composable(route = Route.EditProfile.route) {
            EditProfileScreen(role = Role.TRIAL_PARTICIPANT) {
                navController.popBackStack()
            }
        }

        composable(route = Route.LogInd.route) {
            LogInScreen(navHostController = navController, authViewModel = authViewModel)
        }

//        composable(route = Route.Applied.route) {
//            AppliedScreen(navHostController = navController)
//        }
//
//        composable(route = Route.DeltagerInfo.route) {
//            // TODO - remove hardcoded trialID - navigate with arguments!
//            navBackStackEntry ->
//            val trialID = navBackStackEntry.arguments?.getString("trialID")
//            if(trialID == null) {
//                Toast.makeText(ctx,"TrialID is required", Toast.LENGTH_SHORT).show()
//            } else {
//                DeltagerInfo(trialID = trialID, trialsViewModel) {
//                    navController.navigate(Route.Applied.route)
//            }
//
//            }
//        }
    }
}
fun NavGraphBuilder.navigationAppHost(navController: NavHostController, authViewModel: AuthViewModel?) {
    navigation(route = Graph.SETTING ,startDestination = BottomBarItems.Profile.route) {
        composable(Route.Setting.route) {
            notificationNav(navController= navController)
            SettingsScreen(role = Role.RESEARCHER, authViewModel = authViewModel, onClick =
            {
                navController.popBackStack()
            },
                onClickNav = {
                    navController.navigate(Route.Notification.route)
                },
                logOutNav = {
                    navController.navigate(BottomBarItems.Home.route)
                }
            )
        }
    }
}

fun NavGraphBuilder.notificationNav(navController: NavHostController) {
    navigation(route = Graph.NOTIFICATION, startDestination = BottomBarItems.Profile.route) {
        composable(route = Route.Notification.route) {
            NotificationsScreen() {
                navController.popBackStack()
            }
        }
    }
}





fun NavGraphBuilder.deltagerInfoNav(navController: NavHostController,trialsViewModel: TrialsViewModel) {
    navigation(route = Graph.PARTICIPANT ,startDestination = Route.Applied.route) {

        composable(Route.DeltagerInfo.route) {
            navBackStackEntry ->
            val trialID = navBackStackEntry.arguments?.getString("trialID")
            if(trialID == null) {
                val ctx = LocalContext.current
                Toast.makeText(ctx,"TrialID is required", Toast.LENGTH_SHORT).show()
            } else {
                DeltagerInfo(trialID = trialID, trialsViewModel ,
                    onClick = {
                        navController.navigate(Route.Applied.route)
                },onClickNav = {
                    navController.navigate("Home")
                })
            }
        }

        composable(route = Route.Applied.route) {
            AppliedScreen(navHostController = navController)
        }
        }
    }