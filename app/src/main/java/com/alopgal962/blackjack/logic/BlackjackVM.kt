package com.alopgal962.blackjack.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alopgal962.blackjack.clases.Baraja
import com.alopgal962.blackjack.clases.Carta
import com.alopgal962.blackjack.clases.Jugador


class BlackjackVM():ViewModel() {

    /**
     * Primero creamos las que seran modificables
     */

    private  var _jugador1mutable = MutableLiveData<Jugador>()
    private var _jugador2mutable = MutableLiveData<Jugador>()
    private var _turno1 = MutableLiveData<Boolean>()
    private var _jugadorturno = MutableLiveData<String>()
    private var _ganadorjuego = MutableLiveData<String>()
    private var _juegoterminado = MutableLiveData<Boolean>()
    private var _alguiensehaplantado = MutableLiveData<Boolean>()
    private var _comprobarmas = MutableLiveData<Boolean>()
    private var _imagenwinner = MutableLiveData<String>()


    /**
     * Las que se veran y se invocaran con la variable ObserveAsState
     */

    var jugador1queseve:LiveData<Jugador> = _jugador1mutable
    var jugador2queseve:LiveData<Jugador> = _jugador2mutable
    var jugadorturno:LiveData<String> = _jugadorturno
    var ganadorjuego:LiveData<String> = _ganadorjuego
    var juegoterminado:LiveData<Boolean> = _juegoterminado
    var alguiensehaplantado:LiveData<Boolean> = _alguiensehaplantado
    var imagenwinner:LiveData<String> = _imagenwinner

    /**
     *Inicia el juego por el primer jugador(nosotros) mediante un booleano que hace que empezemos nosotros
     */
    fun iniciarporuser1(){
        _turno1.value=true
    }

    /**
     * Se crea la baraja y se baraja
     */
    fun iniciarbaraja(){
        Baraja.crearbaraja()
        Baraja.barajar()
    }

    /**
     * Booleano que ejecuta la primera parte del codigo de la funcion comprobarganador
     * Se inicializa en falso
     */

    fun comprobarganadorbasiconoiniciado(){
        _comprobarmas.value=false
    }


    /**
     * Nadie se ha plantado aun
     */
    fun iniciarseplanta(){
        _alguiensehaplantado.value=false
    }

    /**
     * Cuando hay un ganador, termina el juego
     */

    fun juegoterminadoestado(){
        _juegoterminado.value=false
    }

    /**
     * Se da un valor inicial al jugadorturno, que es el nombre del jugador del turno actual. Como se empieza por el primer jugador, este lleva inicializado jugador 1
     */
    fun primerojugador1(){
        _jugadorturno.value="JUGADOR 1"
    }

    /**
     * LLama a las demas funciones para tenerlas agrupadas y llamarlas en el main
     */
    fun iniciarpartida(){
        iniciarbaraja()
        eliminarplayer()
        crearplayer()
        comprobarganadorbasiconoiniciado()
        iniciarseplanta()
        inicializarcartas()
        iniciarporuser1()
        primerojugador1()
        juegoterminadoestado()
    }


    /**
     * Se crean los jugadores con valores iniciales
     */

    fun crearplayer(){
        _jugador1mutable.value=Jugador("PLAYER1",false,0 ,mutableListOf<Carta>())
        _jugador2mutable.value=Jugador("PLAYER2",false,0 ,mutableListOf<Carta>())
    }


    /**
     * Elimina el player por si el usuario desea volver a jugar
     */
    fun eliminarplayer(){
        _jugador1mutable.value=null
        _jugador2mutable.value=null
    }

    /**
     * Se inicializan para que cada jugador inicie con una carta
     */
    fun inicializarcartas(){
        _jugador1mutable.value?.anadircartaalista()
        _jugador2mutable.value?.anadircartaalista()
    }

    /**
     * Se mira que jugador es el actual, y segun el jugador, se añade la carta a la lista de cartas
     */
    fun recibecartaplayer(){
        if (_jugadorturno.value=="JUGADOR 1"){
            _jugador1mutable.value!!.anadircartaalista()
        }
        else
            _jugador2mutable.value!!.anadircartaalista()
    }

    /**
     * Se mira que turno es, si es true es jugador 1, si es falso es jugador 2
     */
    fun actualizartextoturno(){
        if (_turno1.value==true){
            _jugadorturno.value="JUGADOR 1"
        }
        else if (_turno1.value==false){
            _jugadorturno.value="JUGADOR 2"
        }
    }

    /**
     *Al pulsar uno de los botones, se llama esta funcion que cambia el turno al opuesto
     */
    fun cambiaturno(){
        if (_alguiensehaplantado.value==false){
            _turno1.value=!_turno1.value!!
        }
    }

    /**
     * Se comprueba quien de los dos jugadores ha ganado
     */
    fun comprobarganador(){
        if (_comprobarmas.value==false){
            //CONDICION DE SI GANA EL JUGADOR 2 ES PORQUE SE PASA EL JUGADOR 1
            if (_jugador1mutable.value!!.contarpuntos()>=21 && _jugador2mutable.value!!.contarpuntos()<21){
                _ganadorjuego.value="JUGADOR 2 WIN"
                _juegoterminado.value=true
                _imagenwinner.value="winner"
            }
            //CONDICION DE SI GANA JUGADOR 1 ES QUE SE PASA JUGADOR 2
            else if (_jugador1mutable.value!!.contarpuntos()<21 && _jugador2mutable.value!!.contarpuntos()>=21){
                _ganadorjuego.value="JUGADOR 1 WIN"
                _juegoterminado.value=true
                _imagenwinner.value="winner"
            }
            else if (_jugador1mutable.value!!.seplanta==true && _jugador2mutable.value!!.seplanta == true){
                _comprobarmas.value=true
            }
        }
        else{
            //SI LOS DOS SE RINDEN Y TIENE MAS PUNTOS EL PRIMERO Y MENOS QUE 21 GANA EL JUGADOR 1
            if (_jugador1mutable.value!!.contarpuntos()>_jugador2mutable.value!!.contarpuntos()){
                _ganadorjuego.value="JUGADOR 1 WIN"
                _juegoterminado.value=true
                _imagenwinner.value="winner"
            }
            //SI LOS DOS SE RINDEN Y TIENE MAS PUNTOS EL SEGUNDO Y MENOS QUE 21 GANA EL JUGADOR 2
            else if (_jugador1mutable.value!!.contarpuntos()<_jugador2mutable.value!!.contarpuntos()){
                _ganadorjuego.value="JUGADOR 2 WIN"
                _juegoterminado.value=true
                _imagenwinner.value="winner"
            }
            //SI NO, EMPATE
            else {
                _ganadorjuego.value="EMPATE"
                _juegoterminado.value=true
                _imagenwinner.value="empate"
            }
        }
    }

    /**
     * Para comprobar cuando un jugador se planta, ademas, su atributo seplanta es true, haciendo que ya no pueda pedir mas cartas
     */
    fun jugadorseplanta(){
        if (jugadorturno.value=="JUGADOR 1"){
            _jugador1mutable.value?.seplanta=true
        }
        else
            _jugador2mutable.value?.seplanta=true
    }

}