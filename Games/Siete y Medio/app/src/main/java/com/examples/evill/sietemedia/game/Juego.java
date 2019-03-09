package com.examples.evill.sietemedia.game;

/**
 * Created by Evill on 30/09/2015.
 */
public class Juego {

    private double puntuacionJugador;
    private double puntuacionBanca;

    public Juego(){
        // Genero las cartas nuevas
        Baraja.getInstance().generaCartas();
        // Puntuaciones iniciales
        puntuacionBanca = 0;
        puntuacionJugador = 0;
    }

    public void inicializar(){
        puntuacionBanca = 0;
        puntuacionJugador = 0;
        Baraja.getInstance().barajar();
    }
    public void addJugadorPuntos(double v){
        puntuacionJugador+=v;
    }

    public void addBancaPuntos(double v){
        puntuacionBanca+=v;
    }


    public double getPuntBanca(){
        return puntuacionBanca;
    }

    public double getPuntJugador(){
        return puntuacionJugador;
    }
}
