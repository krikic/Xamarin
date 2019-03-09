package com.examples.evill.sietemedia.game;

import android.graphics.Bitmap;

/**
 * Created by Evill on 24/09/2015.
 */
public class Carta {
    private int valor;
    private Palo palo;
    private double puntos;


    public Carta(int v, Palo p, double pt){
        valor = v;
        palo = p;
        puntos = pt;

    }

    public double getPuntos(){
        return puntos;
    }

    public String getRepresentacion(){
        return palo.toString().toLowerCase()+valor;
    }


}
