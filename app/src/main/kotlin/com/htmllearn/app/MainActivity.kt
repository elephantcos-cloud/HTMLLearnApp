package com.htmllearn.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.htmllearn.app.data.preferences.UserPreferences
import com.htmllearn.app.data.preferences.dataStore
import com.htmllearn.app.ui.navigation.AppNavHost
import com.htmllearn.app.ui.navigation.Screen
import com.htmllearn.app.ui.theme.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = UserPreferences(this)
        val onboardingDone = runBlocking { prefs.onboardingDone.first() }

        setContent {
            HTMLLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BgPrimary
                ) {
                    val navController = rememberNavController()
                    val appViewModel: com.htmllearn.app.viewmodel.AppViewModel = viewModel()

                    AppNavHost(
                        navController = navController,
                        startDestination = if (onboardingDone) Screen.Dashboard.route else Screen.Onboarding.route,
                        viewModel = appViewModel
                    )
                }
            }
        }
    }
}
