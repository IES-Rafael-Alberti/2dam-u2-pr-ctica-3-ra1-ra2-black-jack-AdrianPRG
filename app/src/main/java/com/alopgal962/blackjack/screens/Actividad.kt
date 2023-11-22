@file:OptIn(ExperimentalMaterial3Api::class)

package com.alopgal962.blackjack.screens

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alopgal962.blackjack.clases.Baraja
import com.alopgal962.blackjack.clases.Jugador
import com.alopgal962.blackjack.clases.Routes

import com.alopgal962.blackjack.ui.theme.BlackJackTheme

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
                imagen(idimagen = "player1")
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
                imagen(idimagen = "player2")
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
    //barajar
    Baraja.crearbaraja()
    Baraja.barajar()
    //estados
    var turno1 by rememberSaveable { mutableStateOf(true) }
    //pantalla
    Column(
        modifier = Modifier
            .background(color = Color(17, 50, 59))
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        if (turno1) {
            Text(text = "TURNO DE ${jugador1.nombre}", modifier = Modifier.padding(bottom = 20.dp))
            Text(
                text = jugador1.nombre,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.background(color = Color.White),
                color = Color.Yellow
            )
            Text(
                text = jugador2.nombre,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .padding(top = 250.dp)
                    .background(color = Color.White)
            )
            Row(modifier = Modifier.padding(top = 250.dp)) {
                botonesuser()
            }
            Button(
                onClick = {turno1=false},
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "VOLVER ATRAS/FINALIZAR")
            }
        } else {
            Text(text = "TURNO DE ${jugador2.nombre}", modifier = Modifier.padding(bottom = 20.dp))
            Text(
                text = jugador1.nombre,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.background(color = Color.White)
            )
            Text(
                text = jugador2.nombre,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .padding(top = 250.dp)
                    .background(color = Color.White)
            )
            Row(modifier = Modifier.padding(top = 250.dp)) {
                botonesuser()
            }
            Button(
                onClick = {turno1=true},
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "VOLVER ATRAS/FINALIZAR")
            }
        }
    }
}

@Composable
fun botonesuser() {
    Button(onClick = { }) {
        Text(text = "DAME CARTA")
    }
    Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 20.dp)) {
        Text(text = "MANTENER")
    }
}

@Composable
fun imagen(idimagen: String) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(painter = painter, contentDescription = "imagenplayer")
}

@Composable
fun mostrarcarta(idimagen: String) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(painter = painter, contentDescription = "carta")
}

@Composable
fun obtenercarta(jugador: Jugador, quien: Boolean) {
    var playerturno by rememberSaveable {
        mutableStateOf(quien)
    }
    if (playerturno) {
        mostrarcarta(idimagen = Baraja.damecarta())
    }

}