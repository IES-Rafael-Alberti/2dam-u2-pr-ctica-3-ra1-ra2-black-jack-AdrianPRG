package com.alopgal962.blackjack.clases

public class Jugador() {

    companion object{

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

        fun inicializarcarta(){
            listacartas.add(Baraja.damecarta())
        }

        fun damecartajugador():Carta{
            return listacartas.last()
        }

        fun contarpuntos():Int{
            var puntos=0
            for (carta in listacartas){
                puntos+=carta.puntosmin
            }
            return puntos
        }
    }

}