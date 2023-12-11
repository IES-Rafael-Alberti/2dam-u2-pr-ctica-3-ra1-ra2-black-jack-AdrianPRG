package com.alopgal962.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alopgal962.blackjack.clases.Routes
import com.alopgal962.blackjack.logic.BlackjackVM
import com.alopgal962.blackjack.uiscreens.screenmenu
import com.alopgal962.blackjack.uiscreens.screenpersonalziar
import com.alopgal962.blackjack.ui.theme.BlackJackTheme

class MainActivity : ComponentActivity() {

   private val BlackJackvm:BlackjackVM by viewModels()
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
                        composable(Routes.screennombre.route) { screenpersonalziar(navcontroller,BlackJackvm) }
                    }
                }
            }
        }
    }
}