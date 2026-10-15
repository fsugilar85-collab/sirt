package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.SplashScreen
import com.example.ui.WebViewScreen
import com.example.ui.theme.MyApplicationTheme

enum class AppScreen {
    SPLASH,
    HOME
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SiRtRwApp()
                }
            }
        }
    }
}

@Composable
fun SiRtRwApp() {
    var currentScreen by rememberSaveable { mutableStateOf(AppScreen.SPLASH) }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "AppScreenTransition"
    ) { screen ->
        when (screen) {
            AppScreen.SPLASH -> {
                SplashScreen(
                    onNavigateToHome = {
                        currentScreen = AppScreen.HOME
                    }
                )
            }
            AppScreen.HOME -> {
                WebViewScreen()
            }
        }
    }
}
