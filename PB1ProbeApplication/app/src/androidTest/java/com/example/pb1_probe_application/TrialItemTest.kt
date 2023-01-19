package com.example.pb1_probe_application

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.pb1_probe_application.dataClasses.Trial
import com.example.pb1_probe_application.ui.TrialItem
import com.example.pb1_probe_application.ui.TrialPostIcons
import org.junit.Rule
import org.junit.Test

class TrialItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    val trial = Trial(title = "Test trial", purpose = "Why not", locations = "Over there")

    @Test
    fun titleTest() {
        composeTestRule.setContent {
            TrialItem(
                trial = trial,
                iconUsed = TrialPostIcons.NotificationOn,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithText("Test trial").assertExists()
    }

    @Test
    fun expandTest() {
        composeTestRule.setContent {
            TrialItem(
                trial = trial,
                iconUsed = TrialPostIcons.NotificationOn,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandMore.toString(), true)
            .assertExists().performClick().assertDoesNotExist()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandLess.toString(), true)
            .assertExists().performClick().assertDoesNotExist()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandMore.toString(), true)
            .assertExists()
    }

    @Test
    fun contentTest() {
        composeTestRule.setContent {
            TrialItem(
                trial = trial,
                iconUsed = TrialPostIcons.NotificationOn,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithText("Formål: Why not").assertExists()
        composeTestRule.onNodeWithText("Lokation: Over there").assertDoesNotExist()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandMore.toString(), true).performClick()
        composeTestRule.onNodeWithText("Lokation: Over there").assertExists()
    }

    @Test
    fun applyTest() {
        var temp = 0
        composeTestRule.setContent {
            TrialItem(
                trial = trial,
                iconUsed = TrialPostIcons.NotificationOn,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {temp++},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithText("Ansøg", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandMore.toString(), true).performClick()
        composeTestRule.onNodeWithText("Ansøg", useUnmergedTree = true).assertExists()
    }

    @Test
    fun notificationOnTest() {
        composeTestRule.setContent {
            var icon = TrialPostIcons.NotificationOn
            TrialItem(
                trial = trial,
                iconUsed = icon,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithContentDescription("notificationsOn")
            .assertIsDisplayed()
    }

    @Test
    fun notificationOffTest() {
        composeTestRule.setContent {
            var icon = TrialPostIcons.NotificationOff
            TrialItem(
                trial = trial,
                iconUsed = icon,
                buttonEnabled = false,
                iconOnClick = {},
                applyOnClick = {},
                readMoreOnClick = {},
            )
        }
        composeTestRule.onNodeWithContentDescription("notificationsOff")
            .assertIsDisplayed()
    }
}


