@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.alopgal962.blackjack.uiscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState

import androidx.navigation.NavHostController
import com.alopgal962.blackjack.clases.Baraja
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
                onClick = { navcontroller.navigate(Routes.screennombre.route) },
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
fun screeninicializacion(navcontroller: NavHostController, blackjackvm:BlackjackVM) {
    blackjackvm.iniciar()
    blackjackvm.crearbaraja()
    blackjackvm.barajar()
    blackjackvm.crearplayer()
    blackjackvm.inicializarcartas()
    blackjackvm.iniciarporuser1()
    blackjackvm.queturnoes()
    blackjackvm.cambiaturno()
    screenplayers(blackjackvm)
}


@Composable
fun screenplayers(blackjackvm: BlackjackVM){
    val turnojugador:String by blackjackvm.jugadorturno.observeAsState(initial = blackjackvm.jugadorturno.value!!)
    val jugador1:Jugador by blackjackvm.jugador1.observeAsState(initial = blackjackvm.jugador1.value!!)

    Column(modifier= Modifier
        .background(color = Color(35, 77, 25))
        .fillMaxSize()) {
        Text(text = "TURNO DE ${turnojugador}")
        Row(modifier = Modifier.padding(2.dp)) {
            Mostrarcarta(idimagen = blackjackvm.damecartaplayer(jugador1), x = 20 , y = 10 )
        }
        Row(modifier = Modifier.padding(top = 140.dp)) {}
        Button(modifier = Modifier.padding(top = 40.dp),onClick = {
            blackjackvm.queturnoes()
            blackjackvm.cambiaturno()}) {
            Text(text = "DAME CARTA")
        }
    }
}


/*
@Composable
fun Screenplayer1(jugador1: Jugador, jugador2:Jugador,blackjackvm: BlackjackVM) {
    var cont = 0
    val continuea:Boolean by blackjackvm.continuar.observeAsState(initial = false)
    if(continuea==false){
        screencompartida(jugador = jugador1)
        Box(modifier=Modifier.padding(end = 180.dp)) {
            for (carta in jugador1.listacartas){
                cont+=35
                Mostrarcarta(idimagen = carta.iddrawable,cont,0)
            }
        }
        Row(modifier = Modifier.padding(top = 150.dp))
        {
            Button(onClick = {
                jugador1.listacartas.add(Baraja.damecarta())
                blackjackvm.setcontinuar()
                blackjackvm.setseecarta()
                            }) { Text(text = "DAME CARTA") }
                            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 20.dp)) { Text(text = "MANTENER") }
        }
    }
    else{
        if (blackjackvm.seecarta.value==true){
            Text(text = "CARTA RECIBIDAD:",modifier = Modifier
                .background(color = Color.Yellow)
                .padding(bottom = 10.dp))
            Mostrarcarta(idimagen = jugador1.damecartajugador().iddrawable,0,0)
            Button(onClick = { blackjackvm.setseecarta() },modifier=Modifier.padding(top = 20.dp)) {
                Text(text = "CONTINUAR")
            }
        }
        else
            Screenplayer2(jugador1 = jugador1 , jugador2 = jugador2, turno = false,false,blackjackvm)
    }
}

@Composable
fun Screenplayer2(jugador1: Jugador, jugador2:Jugador,turno:Boolean,vercarta: Boolean,blackjackvm: BlackjackVM) {
    var cont = 0
    if (blackjackvm.continuar.value==false){
        Column {
            screencompartida(jugador = jugador2)
            Box(modifier=Modifier.padding(end = 150.dp)) {
                for (carta in jugador2.listacartas){
                    cont+=35
                    Mostrarcarta(idimagen = carta.iddrawable, cont,0)
                }
            }
            Row(modifier = Modifier.padding(top = 150.dp)) {
                Button(onClick = { jugador2.listacartas.add(Baraja.damecarta())
                    blackjackvm.setcontinuar()
                    blackjackvm.setseecarta()}) { Text(text = "DAME CARTA") }
                Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 20.dp)) { Text(text = "MANTENER") } }
        }
    }
    else{
        if (blackjackvm.seecarta.value==true){
            Text(text = "CARTA RECIBIDAD:",modifier = Modifier.background(color = Color.Yellow))
            Mostrarcarta(idimagen = jugador2.damecartajugador().iddrawable,0,0)
            Button(onClick = { blackjackvm.setseecarta() },modifier=Modifier.padding(top = 20.dp)) {
                Text(text = "CONTINUAR")
            }
        }
        else
            Screenplayer1(jugador1 = jugador1 , jugador2 = jugador2,blackjackvm)
    }

}

@Composable
fun screencompartida(jugador: Jugador) {
        Text(
            text = "TURNO DE ${jugador.nombre.uppercase()}", modifier = Modifier
                .padding(bottom = 20.dp)
                .background(color = Color(95, 158, 124))
                .border(3.dp, color = Color.Black), fontSize = 20.sp
        )
        Text(
            text = "${jugador.nombre} --> ${jugador.contarpuntos()}",
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.background(color = Color(191, 191, 120)),
            color = Color.Black
        )
    Spacer(modifier = Modifier.padding(bottom = 60.dp))
}

 */

@Composable
fun Imagen(idimagen: String) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(painter = painter, contentDescription = "imagenplayer")
}

@Composable
fun Mostrarcarta(idimagen: String,x:Int,y:Int) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
     Image(painter = painter, contentDescription = "carta", modifier = Modifier
         .size(250.dp, 250.dp)
         .padding(top = 30.dp)
         .offset(x.dp, y.dp)
     )
}