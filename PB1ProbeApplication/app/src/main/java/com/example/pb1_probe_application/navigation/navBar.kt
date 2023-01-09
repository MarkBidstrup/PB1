package com.example.pb1_probe_application.navigation

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
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.ui.*
import com.example.pb1_probe_application.ui.theme.Cairo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen

// code from https://developer.android.com/jetpack/compose/navigation#kts
@Composable
fun MainHome(authViewModel: AuthViewModel, trialsViewModel: TrialsViewModel, userViewModel: UserViewModel){
    val navController = rememberNavController()
    BottomNavGraph(navController = navController, authViewModel = authViewModel, trialsViewModel = trialsViewModel, userViewModel = userViewModel)

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
fun BottomNavGraph(navController: NavHostController, authViewModel: AuthViewModel?, trialsViewModel: TrialsViewModel, userViewModel: UserViewModel) {
    val ctx = LocalContext.current
    NavHost(navController = navController,
        startDestination = BottomBarItems.Home.route
    )
    {

        composable(route = BottomBarItems.Home.route) {
            val loggedIn = authViewModel?.currentUser != null
            val researcher = userViewModel.getUserRole() == Role.RESEARCHER
            val role = if (loggedIn && researcher)
                Role.RESEARCHER
            else
                Role.TRIAL_PARTICIPANT
            TrialListingsScreen(role = role, trialsViewModel = trialsViewModel, navHostController = navController, loggedIn = loggedIn)
        }

        composable(route = Route.Filter.route) {
            FilterScreen(trialsViewModel = trialsViewModel, onClickNav = { navController.navigate("Home") {
                launchSingleTop = true
            } })
        }

        composable(route = BottomBarItems.Trials.route) {
            val loggedIn = authViewModel?.currentUser != null
            if(loggedIn) {
                val researcher = userViewModel.getUserRole() == Role.RESEARCHER
                val role = if (researcher)
                    Role.RESEARCHER
                else
                    Role.TRIAL_PARTICIPANT
                MyTrials(trialsViewModel = trialsViewModel, role = role, navHostController = navController)
            }
            else
                NotLoggedInScreen(logInOnClick = { navController.navigate(Route.LogInd.route) {
                    launchSingleTop = true
                } }, registerOnClick = { navController.navigate(Route.Register.route) {
                    launchSingleTop = true
                } }) {
                    navController.popBackStack() }
        }

        composable(route = BottomBarItems.Profile.route) {
            val loggedIn = authViewModel?.currentUser != null
            if(loggedIn) {
                val researcher = userViewModel.getUserRole() == Role.RESEARCHER
                val role = if (researcher)
                    Role.RESEARCHER
                else
                    Role.TRIAL_PARTICIPANT
                ProfileScreen(role = role, navHostController = navController, userViewModel)
            }
            else
                NotLoggedInScreen(logInOnClick = { navController.navigate(Route.LogInd.route){
                    launchSingleTop = true
                } }, registerOnClick = { navController.navigate(Route.Register.route) {
                    launchSingleTop = true
                } }) {
                    navController.popBackStack() }
        }

        composable(route = Route.EditProfile.route) {
            val researcher = userViewModel.getUserRole() == Role.RESEARCHER
            val role = if (researcher)
                Role.RESEARCHER
            else
                Role.TRIAL_PARTICIPANT
            EditProfileScreen(
                role = role,
                onClick = { navController.popBackStack() },
                deleteNav = { navController.navigate("DeleteProfileScreen") {
                    launchSingleTop = true
                } },
                authViewModel = authViewModel,
                userViewModel = userViewModel
            )
        }

        composable(route = Route.LogInd.route) {
            LogInScreen(navHostController = navController, authViewModel = authViewModel, userViewModel = userViewModel) {
                navController.popBackStack()
            }
        }

        composable(route = Route.NotLoggedIn.route) {
            NotLoggedInScreen(logInOnClick = { navController.navigate(Route.LogInd.route){
                launchSingleTop = true
            } }, registerOnClick = { navController.navigate(Route.Register.route){
                launchSingleTop = true
            } }) {
                navController.popBackStack()
            }
        }

        composable(route = Route.Register.route) {
            RegisterScreen(navHostController = navController, authViewModel = authViewModel, userViewModel = userViewModel) {
                navController.popBackStack()
            }
        }
        composable(route = Route.FurtherInformation.route) {
            val researcher = userViewModel.getUserRole() == Role.RESEARCHER
            val role = if (researcher)
                Role.RESEARCHER
            else
                Role.TRIAL_PARTICIPANT
            //TODO
            FurtherInformationScreen(role, onClick =
            {
                navController.navigate("Home") {
                    launchSingleTop = true
                }
            }
            )
        }

        composable(route = Route.DeleteProfileScreen.route) {
            DeleteProfileScreen(
                onClick = { navController.popBackStack() },
                logOutNav = { navController.navigate(BottomBarItems.Home.route){
                    launchSingleTop = true
                } },
                trialsViewModel = trialsViewModel,
                authViewModel = authViewModel,
                userViewModel = userViewModel
            )
        }

        composable(route = Route.DeleteTrialScreen.route) {
            DeleteTrialScreen(
                onClick = { navController.popBackStack() },
                trialsViewModel = trialsViewModel,
                authViewModel = authViewModel,
                userViewModel = userViewModel
            )
        }

        composable(route = Route.CreateTrial.route) {
            val id: String?
            if(authViewModel?.currentUser != null) {
                id = authViewModel.currentUser!!.uid
            CreateTrialScreen(id, trialsViewModel, { navController.popBackStack() } ) {
                navController.navigate(BottomBarItems.Trials.route){
                    launchSingleTop = true
                } }
            }
        }

        composable(route = Route.EditTrial.route) {
            EditTrialScreen(trialsViewModel, { navController.popBackStack() } ) {
                navController.navigate(BottomBarItems.Trials.route){
                    launchSingleTop = true
                }
            }
        }

        composable(route = Route.ManageTrial.route) {
            ManageTrialScreen(trialsViewModel, { navController.popBackStack() },
                { navController.navigate(Route.EditTrial.route){
                    launchSingleTop = true
                } },
                { navController.navigate(Route.DeltagerListe.route){
                    launchSingleTop = true
                } }) {
                navController.navigate(Route.DeleteTrialScreen.route) {
                    launchSingleTop = true
                }
            }
        }

        composable(route = Route.DeltagerInfo.route) {
            if(trialsViewModel.currentNavTrial != null) {
                DeltagerInfo(trialsViewModel.currentNavTrial!!, trialsViewModel, { navController.popBackStack() }) {
                    navController.navigate(Route.Applied.route) {
                        launchSingleTop = true
                    }
                }
            }
        }

        composable(route = Route.ReadMoreTrialPost.route) {
            ReadMoreTrialPostScreen(trial = trialsViewModel.currentNavTrial!!, navController) {
                navController.popBackStack()
            }
        }

        composable(route = Route.Applied.route) {
            AppliedScreen(navHostController = navController)
        }

        composable(route = Route.ForgottenPassword.route) {
            ForgottenPasswordScreen(authViewModel){
                navController.popBackStack()
            }
        }

        navigationAppHost(navController = navController, authViewModel, trialsViewModel)
        notificationNav(navController = navController)

    }
}

fun NavGraphBuilder.navigationAppHost(navController: NavHostController, authViewModel: AuthViewModel?, trialsViewModel: TrialsViewModel) {
    navigation(route = Graph.SETTING ,startDestination = BottomBarItems.Profile.route) {
        composable(Route.Setting.route) {
            notificationNav(navController= navController)
            SettingsScreen(role = Role.RESEARCHER, authViewModel = authViewModel, onClick =
            {
                navController.popBackStack()
            },
                onClickNav = {
                    navController.navigate(Route.Notification.route) {
                        launchSingleTop = true
                    }
                },
                logOutNav = {
                    trialsViewModel.showFilterResult = false // erases filters if there are any filters in place
                    navController.navigate(BottomBarItems.Home.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.notificationNav(navController: NavHostController) {
    navigation(route = Graph.NOTIFICATION, startDestination = BottomBarItems.Profile.route) {
        composable(route = Route.Notification.route) {
            NotificationsScreen {
                navController.popBackStack()
            }
        }
    }
}
