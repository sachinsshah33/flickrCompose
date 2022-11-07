package com.example.flickr.ui.activities

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flickr.ui.compose.FlickrApp
import com.example.flickr.ui.compose.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun homeScreenTest() {
        composeTestRule.setContent {
            MyApplicationTheme {
                FlickrApp()
            }
        }
    }
}