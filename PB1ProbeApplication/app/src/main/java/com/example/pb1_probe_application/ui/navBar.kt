package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.model.LoggedIn
import com.example.pb1_probe_application.navigation.BottomBarItems

import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.navigation.Graph
import com.example.pb1_probe_application.navigation.Route
import com.example.pb1_probe_application.ui.theme.Cairo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen


@Composable
fun MainHome(authViewModel: AuthViewModel, trialsViewModel: TrialsViewModel){
    val navController = rememberNavController()
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
            addItem(screen = screens, currentDestination =currentDestination , navController =navController )
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
    NavHost(navController = navController,
        startDestination = BottomBarItems.Home.route
    ) {
        composable(route = BottomBarItems.Home.route) {
            TrialListingsScreen(trialsViewModel = trialsViewModel, navHostController = navController, loggedIn = LoggedIn.loggedIn)
        }
        // TODO loggedInd temp solution
        composable(route = Route.HomeLoggedIn.route) {
            TrialListingsScreen(trialsViewModel = trialsViewModel, navHostController = navController, loggedIn = LoggedIn.loggedIn)
        }
        composable(route = BottomBarItems.Trials.route) {
            MyTrials(trialsViewModel = trialsViewModel, navHostController= navController)
        }

        composable(route = BottomBarItems.Profile.route) {

            ProfileScreen(role = Role.TRIAL_PARTICIPANT,navHostController= navController)
        }

        navigationAppHost(navController = navController)
        notificationNav(navController= navController)
        // navigate to editprofile screen
        composable( route = Route.EditProfile.route) {
            EditProfileScreen(role = Role.TRIAL_PARTICIPANT) {
                navController.popBackStack()
            }
        }
        composable( route = Route.LogInd.route) {
            LogIn(navHostController= navController, authViewModel = authViewModel)
        }
        composable( route = Route.Applied.route) {
            AppliedScreen(navHostController= navController)
        }

        composable( route = Route.DeltagerInfo.route) {
            // TODO - remove hardcoded trialID - navigate with arguments!
            DeltagerInfo("5BDtV4LFGXQnWVpgX4tH", trialsViewModel) {
                navController.navigate(Route.Applied.route)
            }
        }
    }
}
fun NavGraphBuilder.navigationAppHost(navController: NavHostController) {
    navigation(route = Graph.SETTING ,startDestination = BottomBarItems.Profile.route) {
       composable(Route.Setting.route) {
           notificationNav(navController= navController)
           SettingsScreen(role = Role.RESEARCHER, onClick =
           {
               navController.popBackStack()
           },
               onClickNav = {
                   navController.navigate(Route.Notification.route)
               }
               )
       }
    }
}
fun NavGraphBuilder.notificationNav(navController: NavHostController) {
    navigation(route = Graph.NOTIFICATION ,startDestination = BottomBarItems.Profile.route) {
        composable(route = Route.Notification.route) {
            NotificationsScreen() {
                navController.popBackStack()
            }
        }
        }
    }


