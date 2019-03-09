package com.examples.evill.sietemedia.game;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Evill on 24/09/2015.
 */
public class Baraja {

    private List<Carta> cartas;
    private List<Integer> orden;
    private boolean cargado = false;

    private static Baraja ourInstance = new Baraja();

    public static Baraja getInstance() {
        return ourInstance;
    }

    private Baraja() {
    }

    /*
     * Genera la baraja vacia e introduce las cartas
     */
    public void generaCartas(){
        if(!cargado){
            orden = new ArrayList<Integer>();
            cartas = new ArrayList<Carta>();
            for(Palo p : Palo.values()){
                for(int i = 1; i <= 7; i++){
                    cartas.add(new Carta(i,p,i));

                }
                for(int i = 10; i <= 12; i++){
                    cartas.add(new Carta(i,p,0.5));
                }
            }
            cargado = true;
        }
        barajar();
    }

    /*
     * Baraja las cartas, la forma de barajar es coger las 40 cartas de la baraja e
     * ir insertandolas en otra baraja
     */
    public void barajar(){
        List<Integer> c = new ArrayList<Integer>();
        orden.clear();
        for(int i = 0; i < cartas.size(); i++){
            orden.add(i);
        }
        Random rnd = new Random();
        while(!orden.isEmpty()){
            int v = rnd.nextInt(orden.size());
            c.add(orden.remove(v));
        }
        orden = c;
    }

    /*
     * Obtiene la primera carta que hay en la baraja
     */
    public Carta obtenerPrimera(){
        return cartas.get(orden.remove(0));
    }

    /*
     * Devuelve si quedan cartas o no
     */
    public boolean quedanCartas(){
        return !orden.isEmpty();
    }


    private Bitmap getBitmapByPos(Bitmap src, int width, int height){
        return Bitmap.createBitmap(src, (width-1)*src.getWidth()/12, (height*src.getHeight())/5, (src.getWidth()/12), (src.getHeight()/5));
    }
}
