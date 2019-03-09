package com.examples.evill.sietemedia.game;



import android.util.Log;

import com.examples.evill.sietemedia.GameActivity;

import java.util.Random;

/**
 * Created by Evill on 30/09/2015.
 */
public class JuegoPresenter {
    private GameActivity view;
    private Juego game;
    private BancaThread bt;

    public JuegoPresenter(GameActivity v, Juego g){
        view = v;
        game = g;
    }

    public void jugadorObtieneCarta(){
        // Es llamado cuando el jugador pulsa el boton
        Carta c = Baraja.getInstance().obtenerPrimera();
        game.addJugadorPuntos(c.getPuntos());
        view.actualizaPuntosJugador(game.getPuntJugador());
        view.mostrarCartaJugador(c);
        if(game.getPuntJugador() > 7.5){
            view.bloqueaCarta();
            view.bloqueaPlantarse();
            // Empieza Banca a jugar
            bt = new BancaThread(this);
            bt.start();
        }
    }


    public void jugadorSePlanta() {
        // Es llamado cuando pulsa el boton plantarse
        view.bloqueaCarta();
        view.bloqueaPlantarse();
        // Empieza Banca a jugar
        bt = new BancaThread(this);
        bt.start();
    }

    public void bancaObtieneCarta(){
        final Carta c = Baraja.getInstance().obtenerPrimera();
        game.addBancaPuntos(c.getPuntos());
        view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.actualizaPuntosBanca(game.getPuntBanca());
                view.mostrarCartaBanca(c);
            }
        });


        if(game.getPuntJugador() > 7.5){
            bt.setRunning(false);
            Log.d("ObtieneCarta", "game.getPuntJugador() > 7.5");
            comprobarGanador();
        }else{
            if(game.getPuntBanca() >= game.getPuntJugador()){
                Log.d("ObtieneCarta", "game.getPuntBanca() >= game.getPuntJugador()");
                bt.setRunning(false);
                comprobarGanador();
            }
        }
    }

    public void comprobarGanador() {
        if (game.getPuntBanca() == 7.5) {
            // Automaticamente gana la banca
            view.ganaBanca();
        } else {
            if (game.getPuntBanca() == game.getPuntJugador()) {
                view.ganaBanca();
            } else {
                if (game.getPuntJugador() > 7.5 && game.getPuntBanca() > 7.5) {
                    // Si ambos pasan de 7.5 no gana nadie
                    view.empate();
                } else {
                    if (game.getPuntJugador() > 7.5) {
                        view.ganaBanca();
                    } else {
                        if (game.getPuntBanca() > game.getPuntJugador() && game.getPuntBanca() <= 7.5) {
                            view.ganaBanca();
                        } else {
                            view.ganaJugador();
                        }
                    }
                }
            }
        }

    }
}
