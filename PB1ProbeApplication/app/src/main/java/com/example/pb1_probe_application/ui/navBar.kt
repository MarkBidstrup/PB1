package com.example.pb1_probe_application.ui



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.BottomBarItems
import com.example.pb1_probe_application.ui.theme.Cairo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen


@Composable
fun MainHome(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarItems.Home,
        BottomBarItems.Trails,
        BottomBarItems.Messages,
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
                color = Color.Black
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
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = BottomBarItems.Home.route
    ) {
        composable(route = BottomBarItems.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarItems.Trails.route) {
            TrialListingsScreen()

        }
        composable(route = BottomBarItems.Messages.route) {
            NotificationsScreen()
        }
        composable(route = BottomBarItems.Profile.route) {

            ProfileScreen(user = "patient" )
        }
    }
}