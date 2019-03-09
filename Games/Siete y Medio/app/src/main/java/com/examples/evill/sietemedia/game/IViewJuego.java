package com.examples.evill.sietemedia.game;

import android.view.View;

/**
 * Created by Evill on 30/09/2015.
 */
public interface IViewJuego {

    void actualizaPuntosJugador(double p);
    void actualizaPuntosBanca(double p);
    void mostrarCartaJugador(Carta c);
    void mostrarCartaBanca(Carta c);
    void jugadorPideCarta(View v);
    void jugadorSePlanta(View v);
    void bloqueaCarta();
    void bloqueaPlantarse();
    void ganaJugador();
    void ganaBanca();
    void empate();

}
