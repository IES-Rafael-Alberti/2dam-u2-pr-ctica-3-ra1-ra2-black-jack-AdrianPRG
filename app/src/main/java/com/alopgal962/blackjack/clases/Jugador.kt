package com.alopgal962.blackjack.clases

public class Jugador( var nombre:String="",var seplanta:Boolean=false, var puntosdinero:Int=0, var listacartas:MutableList<Carta>?=null ) {
    

        fun contarcartas():Int{
            var cont=0
            for (carta in listacartas!!){
                cont+=1
            }
            return cont
        }

        fun anadircartaalista(){
            listacartas!!.add(Baraja.damecarta())
        }

        fun dameimagencartajugador():Carta{
            return listacartas!!.last()
        }

        fun contarpuntos():Int{
            var puntos=0
            for (carta in listacartas!!){
                puntos+=carta.puntosmin
            }
            return puntos
        }

        fun borrarcartas(){
            for (carta in listacartas!!){
                listacartas!!.remove(carta)
            }
        }

}