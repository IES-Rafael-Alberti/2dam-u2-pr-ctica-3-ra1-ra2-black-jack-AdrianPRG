package com.alopgal962.blackjack.clases

sealed class Routes(val route:String) {

    object screenmenu : Routes("menu")
    object screennombre:Routes("nombresjugador")
}
