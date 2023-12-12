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

    private  var _jugador1 = MutableLiveData<Jugador>()
    private var _jugador2 = MutableLiveData<Jugador>()
    private var _start = MutableLiveData<Boolean>()
    private var _continuar = MutableLiveData<Boolean>()
    private var _seecarta = MutableLiveData<Boolean>()
    private var _turno = MutableLiveData<Boolean>()
    private var _jugadorturno = MutableLiveData<String>()


    /**
     * Las que se veran
     */

    var jugador1:LiveData<Jugador> = _jugador1
    var jugador2:LiveData<Jugador> = _jugador2
    var continuar:LiveData<Boolean> = _continuar
    var seecarta:LiveData<Boolean> = _seecarta
    var turno:LiveData<Boolean> = _turno
    var jugadorturno:LiveData<String> = _jugadorturno

    fun setstarttrue(){
        _start.value=true
    }

    fun setcontinuar(){
        _continuar.value=!_continuar.value!!
    }
    fun setseecarta(){
        _seecarta.value=!_seecarta.value!!
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

    fun crearplayer(){
        _jugador1.value=Jugador("ANTONIO", mutableListOf<Carta>())
        _jugador2.value=Jugador("MANUEL", mutableListOf<Carta>())
    }

    fun inicializarcartas(){
        _jugador1.value?.jugadorrecibecarta()
        _jugador2.value?.jugadorrecibecarta()
    }

    fun recibecartaplayer(jugador: Jugador){
        jugador.jugadorrecibecarta()
    }

    fun damecartaplayer(jugador:Jugador): String {
        return jugador.damecartajugador().iddrawable
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








}