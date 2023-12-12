package com.alopgal962.blackjack.clases

public class Jugador( var nombre:String="", var listacartas:MutableList<Carta>?=null ) {
    

        fun contarcartas():Int{
            var cont=0
            for (carta in listacartas!!){
                cont+=1
            }
            return cont
        }

        fun jugadorrecibecarta(){
            listacartas!!.add(Baraja.damecarta())
        }

        fun damecartajugador():Carta{
            return listacartas!!.last()
        }

        fun contarpuntos():Int{
            var puntos=0
            for (carta in listacartas!!){
                puntos+=carta.puntosmin
            }
            return puntos
        }

}