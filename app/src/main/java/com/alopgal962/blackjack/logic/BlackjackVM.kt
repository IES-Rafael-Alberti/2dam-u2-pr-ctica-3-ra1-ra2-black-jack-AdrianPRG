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
    private var _start = MutableLiveData<Boolean>()
    private var _turno = MutableLiveData<Boolean>()
    private var _jugadorturno = MutableLiveData<String>()
    private var _ganadorjuego = MutableLiveData<String>()
    private var _juegoterminado = MutableLiveData<Boolean>()


    /**
     * Las que se veran
     */

    var jugador1queseve:LiveData<Jugador> = _jugador1mutable
    var jugador2queseve:LiveData<Jugador> = _jugador2mutable
    var jugadorturno:LiveData<String> = _jugadorturno
    var ganadorjuego:LiveData<String> = _ganadorjuego
    var juegoterminado:LiveData<Boolean> = _juegoterminado

    fun setstarttrue(){
        _start.value=true
    }
    fun iniciarporuser1(){
        _turno.value=false
    }
    fun iniciar(){
        Baraja.crearbaraja()
        Baraja.barajar()
    }
    fun crearbaraja(){
        Baraja.crearbaraja()
    }
    fun barajar(){
        Baraja.barajar()
    }
    fun juegoterminadoestado(){
        _juegoterminado.value=false
    }

    fun crearplayer(){
        _jugador1mutable.value=Jugador("PLAYER1", mutableListOf<Carta>())
        _jugador2mutable.value=Jugador("PLAYER2", mutableListOf<Carta>())
    }

    fun inicializarcartas(){
        _jugador1mutable.value?.anadircartaalista()
        _jugador2mutable.value?.anadircartaalista()
    }

    fun recibecartaplayer(){
        if (_jugadorturno.value=="JUGADOR 1"){
            _jugador1mutable.value?.anadircartaalista()
        }
        else
            _jugador2mutable.value?.anadircartaalista()
    }

    fun damecartaplayer(jugador:Jugador): String {
        return jugador.dameimagencartajugador().iddrawable
    }

    fun queturnoes(){
        if (_turno.value==false){
            _jugadorturno.value="JUGADOR 1"
        }
        else
            _jugadorturno.value="JUGADOR 2"
    }

    fun cambiaturno(){
        _turno.value=!_turno.value!!
    }

    fun comprobarganador(){
        if (_jugador1mutable.value!!.contarpuntos()>=21 && _jugador2mutable.value!!.contarpuntos()<21){
            _ganadorjuego.value="JUGADOR 2"
            _juegoterminado.value=true
        }
        else if (_jugador1mutable.value!!.contarpuntos()<21 && _jugador2mutable.value!!.contarpuntos()>=21){
            _ganadorjuego.value="JUGADOR 1"
            _juegoterminado.value=true
        }
    }









}