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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.navigation.NavHostController
import com.alopgal962.blackjack.clases.Jugador
import com.alopgal962.blackjack.clases.Routes
import com.alopgal962.blackjack.logic.BlackjackVM

/**
 * @param navcontroller sirve para navegar entre pantallas, en este caso esta será la inicial
 */
@Composable
fun screenmenu(navcontroller: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(15, 41, 15)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Imagen(idimagen = "blackjack")
        Row(modifier = Modifier.padding(top = 40.dp)) {
            Button(
                onClick = { navcontroller.navigate(Routes.screeninicial.route) },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier.size(120.dp,100.dp)
            ) {
                Text(text = "1 vs 1", fontSize = 20.sp)
            }
        }
    }
}

/**
 * @param navcontroller sirve para navegar entre pantallas, se navega a esta y se inicializa los valores
 * @param blackjackvm para acceder a los metodos del blackjack
 */
@Composable
fun screeninicializacion(navcontroller: NavHostController, blackjackvm: BlackjackVM) {
    blackjackvm.iniciarpartida()
    screenplayers(navcontroller,blackjackvm)
}


/**
 * @param navcontroller sirve para navegar entre pantallas
 * @param blackjackvm para acceder a las variables y metodos del blackjack
 */
@Composable
fun screenplayers(navcontroller: NavHostController,blackjackvm: BlackjackVM) {
    //Variables observeAsState
    val turnojugador: String by blackjackvm.jugadorturno.observeAsState(initial = blackjackvm.jugadorturno.value!!)
    val jugador1: Jugador by blackjackvm.jugador1queseve.observeAsState(initial = blackjackvm.jugador1queseve.value!!)
    val jugador2: Jugador by blackjackvm.jugador2queseve.observeAsState(initial = blackjackvm.jugador2queseve.value!!)
    val ganadorjuego: String by blackjackvm.ganadorjuego.observeAsState(initial = "")
    val secontinuapartida: Boolean by blackjackvm.juegoterminado.observeAsState(initial = blackjackvm.juegoterminado.value!!)
    val imagenfinal:String by blackjackvm.imagenwinner.observeAsState(initial = "")
    if (secontinuapartida == false) {
        Column(
            modifier = Modifier
                .background(color = Color(35, 77, 25))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TURNO DE ${turnojugador}", fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color(95, 158, 124))
                    .border(3.dp, color = Color.Black)
                    .padding(3.dp),
                fontSize = 20.sp,
            )
            Column( modifier = Modifier.padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "JUGADOR 2 | Points --> ${jugador2.contarpuntos()}", color = Color(255, 255, 230), fontWeight = FontWeight.Bold, modifier  = Modifier
                    .border(2.dp, Color.LightGray)
                    .background(color = Color(130, 130, 96))
                    .padding(3.dp))
            }
            /*
                Box que contiene un bucle con un contador que, por cada carta, llamara a la funcion mostrarcarta, y recibira las cartas de la lista
                y las mostrará, con un contador que ira desplazando la carta a la derecha por cada carta
             */
            Box(modifier=Modifier.padding(end = 180.dp)) {
                val listamano=jugador2.listacartas!!.size
                recorrer(jugador = jugador2, listamano)
            }
            Column(modifier = Modifier.padding(top = 50.dp)) {
                Text(text = "JUGADOR 1 | Points --> ${jugador1.contarpuntos()}",color = Color(255, 255, 230), fontWeight = FontWeight.Bold, modifier  = Modifier
                    .border(2.dp, Color.LightGray)
                    .background(color = Color(130, 130, 96))
                    .padding(3.dp))
            }
            Box(modifier=Modifier.padding(end = 180.dp)) {
                val listamano=jugador1.listacartas!!.size
                recorrer(jugador = jugador1, listamano)
            }
            Row(modifier = Modifier.padding(top = 40.dp)) {
                /*
                Boton que hace que el jugador que haya pulsado reciba la carta
                Luego se cambia de turno, se mira quien toca ahora, y se comprueba el ganador
                 */
                Button(onClick = {
                    blackjackvm.recibecartaplayer()
                    blackjackvm.cambiaturno()
                    blackjackvm.actualizartextoturno()
                    blackjackvm.comprobarganador()
                }, colors = ButtonDefaults.buttonColors(Color(119, 158, 133))) {
                    Text(text = "DAME CARTA", color = Color.Black)
                }
                /*
                Boton dame carta cambia de turno sin recibir carta
                Jugador se planta
                 */
                Button(modifier = Modifier.padding(start = 20.dp), onClick = {
                    blackjackvm.cambiaturno()
                    blackjackvm.jugadorseplanta()
                    blackjackvm.actualizartextoturno()
                    blackjackvm.comprobarganador()
                }) {
                    Text(text = "PASO")
                }
            }
        }
        //Cuando alguien gana o hay empate sale la opcion de volver atras y el recuento de los puntos de los jugadores
    } else Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(196, 165, 81)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "FIN DE PARTIDA", fontWeight = FontWeight.ExtraBold ,fontSize = 27.sp)
        Text(text = "GANADOR: ${ganadorjuego} \n P1 Points:${jugador1.contarpuntos()} \nP2 Points:${jugador2.contarpuntos()}", color = Color.White , fontWeight = FontWeight.Bold, fontSize = 22.sp, textAlign = TextAlign.Center)
        Column(modifier = Modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Imagen(idimagen = imagenfinal)
            Button(onClick = {
                navcontroller.navigate(Routes.screenmenu.route) }, modifier = Modifier.padding(top = 15.dp)) {
                Text(text = "SALIR AL MENU PRINCIPAL")
            }
        }
    }

}

/**
 * Fun para mostrar imagen
 * @param idimagen es el string iddrawable que dibujara la imagen
 */

@Composable
fun Imagen(idimagen: String) {
    var context = LocalContext.current
    var obtenerid = context.resources.getIdentifier(idimagen, "drawable", context.packageName)
    var painter = painterResource(id = obtenerid)
    Image(painter = painter, contentDescription = "imagenplayer")
}

/**
 * Funcion recorrer recorre la lista de jugadores, y llama a la funcion carta para mostrarla
 */
@Composable
fun recorrer(jugador: Jugador,numero:Int){
    var cont=0
    for (carta in jugador.listacartas!!){
        Mostrarcarta(idimagen = carta.iddrawable,cont,0)
        cont+=35
    }
}


/**
 * Mostrarcarta recibe tres parametros para mostrar la imagen
 * @param idimagen es el iddrawable
 * @param x es para que cada carta este una posicion mas a la derecha
 * @param y es para que cada carta suba, pero este no se utilizara pues la carta solo sigue linea continua en x
 */
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