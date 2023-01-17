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


    @Test
    fun test1() {
        composeTestRule.setContent {
            val trial = Trial(title = "Test trial")
            var applyButton = 0

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
        composeTestRule.onNodeWithContentDescription("notificationsOn").assertIsDisplayed()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandMore.toString(), true).assertExists().performClick()
        composeTestRule.onNodeWithTag(Icons.Filled.ExpandLess.toString(), true).assertExists()
        composeTestRule.onNodeWithText("Ansøg", useUnmergedTree = true).assertExists()
    }
}


