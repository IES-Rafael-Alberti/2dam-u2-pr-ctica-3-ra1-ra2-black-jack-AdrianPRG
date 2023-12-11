@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.alopgal962.blackjack.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alopgal962.blackjack.clases.Baraja
import com.alopgal962.blackjack.clases.Carta
import com.alopgal962.blackjack.clases.Jugador
import com.alopgal962.blackjack.clases.Routes

import com.alopgal962.blackjack.ui.theme.BlackJackTheme
import kotlinx.coroutines.delay
import java.sql.Time

class Actividad : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackJackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun screenpersonalziar(navcontroller: NavHostController) {
    var nombre1 by rememberSaveable {
        mutableStateOf("")
    }
    var nombre2 by rememberSaveable {
        mutableStateOf("")
    }
    var jugador1introduce by rememberSaveable { mutableStateOf(true) }
    var irpartida by rememberSaveable { mutableStateOf(false) }
    if (irpartida == false) {
        if (jugador1introduce) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(95, 173, 132)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Imagen(idimagen = "player1")
                Text(
                    text = "INTRODUCE NOMBRE JUGADOR 1",
                    color = Color.White,
                    modifier = Modifier.padding(top = 40.dp)
                )
                TextField(
                    value = nombre1,
                    onValueChange = { nombre1 = it },
                    label = { Text(text = "Nombre Jugador 1") },
                    modifier = Modifier.padding(top = 20.dp)
                )
                if (nombre1.isNotEmpty()) {
                    Button(
                        onClick = { jugador1introduce = false },
                        modifier = Modifier.padding(40.dp)
                    ) {
                        Text(text = "CONFIRMAR PLAYER 1")
                    }
                }
            }
        } else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(149, 153, 81)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Imagen(idimagen = "player2")
                Text(
                    text = "INTRODUCE NOMBRE JUGADOR 2",
                    color = Color.White,
                    modifier = Modifier.padding(top = 40.dp)
                )
                TextField(
                    value = nombre2,
                    onValueChange = { nombre2 = it },
                    label = { Text(text = "Nombre Jugador 2") },
                    modifier = Modifier.padding(top = 20.dp)
                )
                if (nombre2.isNotEmpty()) {
                    Button(onClick = { irpartida = true }, modifier = Modifier.padding(40.dp)) {
                        Text(text = "CONFIRMAR PLAYER 2")
                    }
                }
            }
    } else
        screenplayervsplayer(nombre1, nombre2)
}


@Composable
fun screenplayervsplayer(nomp1: String, nomp2: String) {
    //jugadores
    var jugador1 = Jugador()
    var jugador2 = Jugador()
    //nombres
    jugador1.nombre = nomp1
    jugador2.nombre = nomp2
    jugador1.inicializarcarta()
    jugador2.inicializarcarta()
    //barajar
    Baraja.crearbaraja()
    Baraja.barajar()
    //pantalla
    Column(
        modifier = Modifier
            .background(color = Color(17, 50, 59))
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Screenplayer1(jugador1 = jugador1, jugador2 = jugador2,false,false)
    }
}

@Composable
fun Screenplayer1(jugador1: Jugador, jugador2:Jugador,turno:Boolean,vercarta:Boolean) {
    var siguiente by rememberSaveable { mutableStateOf(turno) }
    var verlacarta by rememberSaveable { mutableStateOf(vercarta) }
    var cont = 0
    if(!siguiente){
        screencompartida(jugador = jugador1)
        Box(modifier=Modifier.padding(end = 180.dp)) {
            for (carta in jugador1.listacartas){
                cont+=35
                Mostrarcarta(idimagen = carta.iddrawable,cont,0)
            }
        }
        Row(modifier = Modifier.padding(top = 150.dp))
        {
            Button(onClick = { jugador1.listacartas.add(Baraja.damecarta())
                siguiente=!siguiente
            verlacarta=true}) { Text(text = "DAME CARTA") }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 20.dp)) { Text(text = "MANTENER") }
        }
    }
    else{
        if (verlacarta){
            Text(text = "CARTA RECIBIDAD:",modifier = Modifier
                .background(color = Color.Yellow)
                .padding(bottom = 10.dp))
            Mostrarcarta(idimagen = jugador1.damecartajugador().iddrawable,0,0)
            Button(onClick = { verlacarta=false },modifier=Modifier.padding(top = 20.dp)) {
                Text(text = "CONTINUAR")
            }
        }
        else
            Screenplayer2(jugador1 = jugador1 , jugador2 = jugador2, turno = false,false)
    }
}

@Composable
fun Screenplayer2(jugador1: Jugador, jugador2:Jugador,turno:Boolean,vercarta: Boolean) {
    var siguiente by rememberSaveable {
        mutableStateOf(turno) }
    var verlacarta by rememberSaveable { mutableStateOf(vercarta) }
    var cont = 0
    if (!siguiente){
        screencompartida(jugador = jugador2)
        Box(modifier=Modifier.padding(end = 150.dp)) {
            for (carta in jugador2.listacartas){
                cont+=35
                Mostrarcarta(idimagen = carta.iddrawable, cont,0)
            }
        }
        Row(modifier = Modifier.padding(top = 150.dp)) {
            Button(onClick = { jugador2.listacartas.add(Baraja.damecarta())
            siguiente=!siguiente
            verlacarta=true}) { Text(text = "DAME CARTA") }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 20.dp)) { Text(text = "MANTENER") } }
    }
    else{
        if (verlacarta){
            Text(text = "CARTA RECIBIDAD:",modifier = Modifier.background(color = Color.Yellow))
            Mostrarcarta(idimagen = jugador2.damecartajugador().iddrawable,0,0)
            Button(onClick = { verlacarta=false },modifier=Modifier.padding(top = 20.dp)) {
                Text(text = "CONTINUAR")
            }
        }
        else
            Screenplayer1(jugador1 = jugador1 , jugador2 = jugador2, turno = false,false)
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