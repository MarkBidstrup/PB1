package com.example.pb1_probe_application.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItems (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarItems(
        route = "Home",
        title = "Hjem",
        icon = Icons.Default.Home
    )
    object Trails : BottomBarItems(
        route = "Trials",
        title = "Mine studier",
        icon = Icons.Default.ListAlt
    )
    object Messages : BottomBarItems(
        route = "Messages",
        title = "Beskeder",
        icon = Icons.Default.Email
    )
    object Profile : BottomBarItems(
        route = "Profile",
        title = "Min profil",
        icon = Icons.Default.Person
    )
}
