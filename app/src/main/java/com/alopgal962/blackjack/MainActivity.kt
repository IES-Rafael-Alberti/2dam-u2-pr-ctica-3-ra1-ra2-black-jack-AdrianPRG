package com.alopgal962.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alopgal962.blackjack.clases.Routes
import com.alopgal962.blackjack.screens.screenmenu
import com.alopgal962.blackjack.screens.screenplayervsplayer
import com.alopgal962.blackjack.ui.theme.BlackJackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackJackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navcontroller = rememberNavController()
                    NavHost(
                        navController = navcontroller,
                        startDestination = Routes.screenmenu.route
                    ) {
                        composable(Routes.screenmenu.route) { screenmenu(navcontroller) }
                        composable(Routes.screenpvp.route) { screenplayervsplayer(navcontroller) }
                    }
                }
            }
        }
    }
}