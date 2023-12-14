@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.alopgal962.blackjack.uiscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextAlign

import androidx.navigation.NavHostController
import com.alopgal962.blackjack.clases.Jugador
import com.alopgal962.blackjack.clases.Routes
import com.alopgal962.blackjack.logic.BlackjackVM

@Composable
fun screenmenu(navcontroller: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(15, 41, 15)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        var idimagen = context.resources.getIdentifier("blackjack", "drawable", context.packageName)
        var painter = painterResource(id = idimagen)
        Image(painter = painter, contentDescription = "blackjack")
        Row(modifier = Modifier.padding(top = 40.dp)) {
            Button(
                onClick = { navcontroller.navigate(Routes.screeninicial.route) },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "1 VS 1")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(start = 30.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = "1 VS AI")
            }
        }
    }
}

@Composable
fun screeninicializacion(navcontroller: NavHostController, blackjackvm: BlackjackVM) {
    blackjackvm.iniciar()
    blackjackvm.crearbaraja()
    blackjackvm.barajar()
    blackjackvm.crearplayer()
    blackjackvm.inicializarcartas()
    blackjackvm.iniciarporuser1()
    blackjackvm.primerojugador1()
    blackjackvm.juegoterminadoestado()
    screenplayers(navcontroller,blackjackvm)
}


@Composable
fun screenplayers(navcontroller: NavHostController,blackjackvm: BlackjackVM) {
    val turnojugador: String by blackjackvm.jugadorturno.observeAsState(initial = blackjackvm.jugadorturno.value!!)
    val jugador1: Jugador by blackjackvm.jugador1queseve.observeAsState(initial = blackjackvm.jugador1queseve.value!!)
    val jugador2: Jugador by blackjackvm.jugador2queseve.observeAsState(initial = blackjackvm.jugador2queseve.value!!)
    val ganadorjuego: String by blackjackvm.ganadorjuego.observeAsState(initial = "")
    val secontinuapartida: Boolean by blackjackvm.juegoterminado.observeAsState(initial = blackjackvm.juegoterminado.value!!)

    if (secontinuapartida == false) {
        Column(
            modifier = Modifier
                .background(color = Color(35, 77, 25))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TURNO DE ${turnojugador}", textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color(95, 158, 124))
                    .border(3.dp, color = Color.Black),
                fontSize = 20.sp,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "JUGADOR 2")
                Text(text = "PUNTOS -> ${jugador2.contarpuntos()}")
            }
            Box(modifier=Modifier.padding(end = 180.dp)) {
                var cont=0
                for (carta in jugador2.listacartas!!){
                    Mostrarcarta(idimagen = carta.iddrawable,cont,0)
                    cont+=35
                }
            }
            Column(modifier = Modifier.padding(top = 100.dp)) {
                Text(text = "JUGADOR 1")
                Text(text = "PUNTOS -> ${jugador1.contarpuntos()}")
            }
            Box(modifier=Modifier.padding(end = 150.dp)) {
                var cont=0
                for (carta in jugador1.listacartas!!){
                    Mostrarcarta(idimagen = carta.iddrawable,cont,0)
                    cont+=35
                }
            }
            Row(modifier = Modifier.padding(top = 40.dp)) {
                Button(onClick = {
                    blackjackvm.recibecartaplayer()
                    blackjackvm.cambiaturno()
                    blackjackvm.queturnoes()
                    blackjackvm.comprobarganador()
                }, colors = ButtonDefaults.buttonColors(Color(119, 158, 133))) {
                    Text(text = "DAME CARTA", color = Color.Black)
                }
                Button(modifier = Modifier.padding(start = 20.dp), onClick = {
                    blackjackvm.cambiaturno()
                    blackjackvm.queturnoes()
                }) {
                    Text(text = "PLANTARME")
                }
            }
        }
    } else Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(196, 165, 81)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "FIN DE PARTIDA", fontSize = 24.sp)
        Text(text = "GANADOR: ${ganadorjuego}", fontSize = 18.sp)
        Row(modifier = Modifier.padding(top = 80.dp)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "VOLVER A JUGAR")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 40.dp)) {
                Text(text = "SALIR AL MENU PRINCIPAL")
            }
        }
    }

}

@Composable
fun Imagen(idimagen: String) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(painter = painter, contentDescription = "imagenplayer")
}

@Composable
fun Mostrarcarta(idimagen: String, x: Int, y: Int) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(
        painter = painter,
        contentDescription = "carta",
        modifier = Modifier
            .size(250.dp, 250.dp)
            .padding(top = 30.dp)
            .offset(x.dp, y.dp)
    )
}