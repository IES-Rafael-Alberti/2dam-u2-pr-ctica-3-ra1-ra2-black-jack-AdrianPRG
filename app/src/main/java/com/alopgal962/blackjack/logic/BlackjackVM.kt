package com.alopgal962.blackjack.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alopgal962.blackjack.clases.Jugador


class BlackjackVM():ViewModel() {

    /**
     * Primero creamos las que seran modificables
     */

    private var _jugador1 = MutableLiveData<Jugador>()
    private var _jugador2 = MutableLiveData<Jugador>()
    private val _first = MutableLiveData<Boolean>()
    private val _start = MutableLiveData<Boolean>()


    /**
     * Las que se veran
     */

    val seejugador1:LiveData<Jugador> = _jugador1
    val seejugador2:LiveData<Jugador> = _jugador2
    val _player1name = MutableLiveData<String>()
    val _player2name = MutableLiveData<String>()
    val first:LiveData<Boolean> = _first



    fun setplayername(quejugador:Int,name:String){
      when(quejugador){
          1 -> _jugador1.value?.nombre=name
          2 -> _jugador2.value?.nombre=name
      }
  }

    fun cambiaranamejugador2(){
        _first.value=true
    }






}