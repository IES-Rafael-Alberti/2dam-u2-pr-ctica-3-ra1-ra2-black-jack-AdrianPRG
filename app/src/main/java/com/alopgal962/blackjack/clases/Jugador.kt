package com.alopgal962.blackjack.clases

class Jugador() {
    var nombre=""
    var turno=Boolean
    var ganador=Boolean
    var listacartas= mutableListOf<Carta>()

    fun contarcartas():Int{
        var cont=0
        for (carta in listacartas){
            cont+=1
        }
        return cont
    }

    fun contarpuntos():Int{
        var puntos=0
        for (carta in listacartas){
            puntos+=carta.puntosmin
        }
        return puntos
    }




}